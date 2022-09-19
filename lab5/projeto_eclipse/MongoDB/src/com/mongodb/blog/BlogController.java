/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.blog;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.bson.Document;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import javax.servlet.http.Cookie; 

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static com.mongodb.util.Helpers.printJson;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.setPort;

/**
 * This class encapsulates the controllers for the blog web application.  It delegates all interaction with MongoDB
 * to three Data Access Objects (DAOs).
 * <p/>
 * It is also the entry point into the web application.
 */
public class BlogController {
    private final Configuration cfg;
    private final BlogPostDAO blogPostDAO;
    private final UserDAO userDAO;
    private final SessionDAO sessionDAO;

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            new BlogController("mongodb://localhost");
        }
        else {
            new BlogController(args[0]);
        }
    }

    public BlogController(String mongoURIString) throws IOException {
        final MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURIString));
        final MongoDatabase blogDatabase = mongoClient.getDatabase("blog");

        blogPostDAO = new BlogPostDAO(blogDatabase);
        userDAO = new UserDAO(blogDatabase);
        sessionDAO = new SessionDAO(blogDatabase);

        cfg = createFreemarkerConfiguration();
        Spark.port(8082);
        initializeRoutes();
    }

    private void initializeRoutes() throws IOException {

    	// this is the blog home page
        get("/", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                String username = sessionDAO.findUserNameBySessionId(getSessionCookie(req));

                List<Document> posts = blogPostDAO.findByDateDescending(10);
                SimpleHash root = new SimpleHash();
                root.put("myposts", posts);
                if (username != null) {
                    root.put("username", username);
                }

                Template template = cfg.getTemplate("blog_template.ftl");
                template.process(root, writer);
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });

        // used to display actual blog post detail page
        get("/post/:permalink", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                String permalink = req.params(":permalink");

                System.out.println("/post: get " + permalink);

                Document post = blogPostDAO.findByPermalink(permalink);
                if (post == null) {
                    System.out.println("post Document is null");
                    res.redirect("/post_not_found");
                }
                else {
                    // empty comment to hold new comment in form at bottom of blog entry detail page
                    SimpleHash newComment = new SimpleHash();
                    newComment.put("name", "");
                    newComment.put("email", "");
                    newComment.put("body", "");

                    SimpleHash root = new SimpleHash();

                    root.put("post", post);
                    root.put("comment", newComment);

	                Template template = cfg.getTemplate("entry_template.ftl");
	                template.process(root, writer);
                }
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });
        
        
        // handle the signup post
        post("/signup", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                String email = req.queryParams("email");
                String username = req.queryParams("username");
                String password = req.queryParams("password");
                String verify = req.queryParams("verify");

                HashMap<String, String> root = new HashMap<String, String>();
                root.put("username", StringEscapeUtils.escapeHtml4(username));
                root.put("email", StringEscapeUtils.escapeHtml4(email));

                Template template = cfg.getTemplate("signup.ftl");
                if (validateSignup(username, password, verify, email, root)) {
                    // good user
                    System.out.println("Signup: Creating user with: " + username + " " + password);
                    if (!userDAO.addUser(username, password, email)) {
                        // duplicate user
                        root.put("username_error", "Username already in use, Please choose another");
                        template.process(root, writer);
                    }
                    else {
                        // good user, let's start a session
                        String sessionID = sessionDAO.startSession(username);
                        System.out.println("Session ID is" + sessionID);

                        res.raw().addCookie(new Cookie("session", sessionID));
                        res.redirect("/welcome");
                    }
                }
                else {
                    // bad signup
                    System.out.println("User Registration did not validate");
                    template.process(root, writer);
                }
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });

        // present signup form for blog
        get("/signup", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                SimpleHash root = new SimpleHash();

                // initialize values for the form.
                root.put("username", "");
                root.put("password", "");
                root.put("email", "");
                root.put("password_error", "");
                root.put("username_error", "");
                root.put("email_error", "");
                root.put("verify_error", "");

                Template template = cfg.getTemplate("signup.ftl");
                template.process(root, writer);
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });


        // welcome page
        get("/welcome", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                String cookie = getSessionCookie(req);
                String username = sessionDAO.findUserNameBySessionId(cookie);

                if (username == null) {
                    System.out.println("welcome() can't identify the user, redirecting to signup");
                    res.redirect("/signup");

                }
                else {
                    SimpleHash root = new SimpleHash();

                    root.put("username", username);

                    Template template = cfg.getTemplate("welcome.ftl");
                    template.process(root, writer);
                }
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });

        // will present the form used to process new blog posts
        get("/newpost", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                // get cookie
                String username = sessionDAO.findUserNameBySessionId(getSessionCookie(req));

                if (username == null) {
                    // looks like a bad request. user is not logged in
                    res.redirect("/login");
                } else {
                    SimpleHash root = new SimpleHash();
                    root.put("username", username);

                    Template template = cfg.getTemplate("newpost_template.ftl");
                    template.process(root, writer);
                }
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });

        // handle the new post submission
        post("/newpost", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                String title = StringEscapeUtils.escapeHtml4(req.queryParams("subject"));
                String post = StringEscapeUtils.escapeHtml4(req.queryParams("body"));
                String tags = StringEscapeUtils.escapeHtml4(req.queryParams("tags"));

                String username = sessionDAO.findUserNameBySessionId(getSessionCookie(req));

                if (username == null) {
                    res.redirect("/login");    // only logged in users can post to blog
                }
                else if (title.equals("") || post.equals("")) {
                    // redisplay page with errors
                    HashMap<String, String> root = new HashMap<String, String>();
                    root.put("errors", "post must contain a title and blog entry.");
                    root.put("subject", title);
                    root.put("username", username);
                    root.put("tags", tags);
                    root.put("body", post);
                    
                    Template template = cfg.getTemplate("newpost_template.ftl");
                    template.process(root, writer);
                }
                else {
                    // extract tags
                    ArrayList<String> tagsArray = extractTags(tags);

                    // substitute some <p> for the paragraph breaks
                    post = post.replaceAll("\\r?\\n", "<p>");

                    String permalink = blogPostDAO.addPost(title, post, tagsArray, username);

                    // now redirect to the blog permalink
                    res.redirect("/post/" + permalink);
                }
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });

        // process a new comment
        post("/newcomment", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {

                String name = StringEscapeUtils.escapeHtml4(req.queryParams("commentName"));
                String email = StringEscapeUtils.escapeHtml4(req.queryParams("commentEmail"));
                String body = StringEscapeUtils.escapeHtml4(req.queryParams("commentBody"));
                String permalink = req.queryParams("permalink");

                Document post = blogPostDAO.findByPermalink(permalink);
                if (post == null) {
                    res.redirect("/post_not_found");
                }
                // check that comment is good
                else if (name.equals("") || body.equals("")) {
                    // bounce this back to the user for correction
                    SimpleHash root = new SimpleHash();
                    SimpleHash comment = new SimpleHash();

                    comment.put("name", name);
                    comment.put("email", email);
                    comment.put("body", body);
                    root.put("comments", comment);
                    root.put("post", post);
                    root.put("errors", "Post must contain your name and an actual comment");

                    Template template = cfg.getTemplate("entry_template.ftl");
                    template.process(root, writer);
                } else {
                    blogPostDAO.addPostComment(name, email, body, permalink);
                    
                    res.redirect("/post/" + permalink);
                }
            	
            	
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });

        // present the login page
        get("/login", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                SimpleHash root = new SimpleHash();

                root.put("username", "");
                root.put("login_error", "");

                Template template = cfg.getTemplate("login.ftl");
                template.process(root, writer);
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });
        

        // process output coming from login form. On success redirect folks to the welcome page
        // on failure, just return an error and let them try again.
        post("/login", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                String username = req.queryParams("username");
                String password = req.queryParams("password");

                System.out.println("Login: User submitted: #" + username + "#  #" + password + "#");

                Document user = userDAO.validateLogin(username, password);

                System.out.println("LOGIN OK");
                
                if (user != null) {

                    System.out.println("User is not null");

                    printJson(user);

                    // valid user, let's log them in
                    String sessionID = sessionDAO.startSession(user.get("_id").toString());

                    System.out.println("Session ID "+sessionID);

                    
                    if (sessionID == null) {
                        res.redirect("/internal_error");
                    }
                    else {
                        // set the cookie for the user's browser
                        res.raw().addCookie(new Cookie("session", sessionID));

                        res.redirect("/welcome");
                    }
                }
                else {
                    SimpleHash root = new SimpleHash();
                    root.put("username", StringEscapeUtils.escapeHtml4(username));
                    root.put("password", "");
                    root.put("login_error", "Invalid Login");
                    Template template = cfg.getTemplate("login.ftl");
                    template.process(root, writer);
                }
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });

        // Show the posts filed under a certain tag
        get("/tag/:thetag", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                String username = sessionDAO.findUserNameBySessionId(getSessionCookie(req));
                SimpleHash root = new SimpleHash();

                String tag = StringEscapeUtils.escapeHtml4(req.params(":thetag"));
                List<Document> posts = blogPostDAO.findByTagDateDescending(tag);

                root.put("myposts", posts);
                if (username != null) {
                    root.put("username", username);
                }
            	
                Template template = cfg.getTemplate("blog_template.ftl");
                template.process(root, writer);
                
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });

        
        // will allow a user to click Like on a post
        post("/like", (req, res) -> {
            StringWriter writer = new StringWriter();
            try {
                String permalink = req.queryParams("permalink");
                String commentOrdinalStr = req.queryParams("comment_ordinal");

                // look up the post in question
                int ordinal = Integer.parseInt(commentOrdinalStr);
                
                // TODO: check return or have checkSession throw
                String username = sessionDAO.findUserNameBySessionId(getSessionCookie(req));
                Document post = blogPostDAO.findByPermalink(permalink);

                //  if post not found, redirect to post not found error
                if (post == null) {
                    res.redirect("/post_not_found");
                }
                else {
                    blogPostDAO.likePost(permalink, ordinal);

                    res.redirect("/post/" + permalink);
                }
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });
        
        // allows the user to logout of the blog
        get("/post_not_found", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                SimpleHash root = new SimpleHash();
                Template template = cfg.getTemplate("post_not_found.ftl");
                template.process(root, writer);
                
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });
        
        
        // allows the user to logout of the blog
        get("/logout", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                String sessionID = getSessionCookie(req);

                if (sessionID == null) {
                    // no session to end
                    res.redirect("/login");
                }
                else {
                    // deletes from session table
                    sessionDAO.endSession(sessionID);

                    // this should delete the cookie
                    Cookie c = getSessionCookieActual(req);
                    c.setMaxAge(0);

                    res.raw().addCookie(c);

                    res.redirect("/login");
                }
            } catch (Exception e) {
                res.redirect("/internal_error");
                e.printStackTrace();
            }
        	return writer.toString();
        });
        
        // used to process internal errors
        get("/internal_error", (req, res) -> {
        	
            StringWriter writer = new StringWriter();
            try {
                SimpleHash root = new SimpleHash();

                root.put("error", "System has encountered an error.");
                Template template = cfg.getTemplate("error_template.ftl");
                template.process(root, writer);
            } catch (Exception e) {
                Spark.halt(500);
                e.printStackTrace();
            }
        	return writer.toString();
        });
    }

    // helper function to get session cookie as string
    private String getSessionCookie(final Request request) {
        if (request.raw().getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.raw().getCookies()) {
            if (cookie.getName().equals("session")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    // helper function to get session cookie as string
    private Cookie getSessionCookieActual(final Request request) {
        if (request.raw().getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.raw().getCookies()) {
            if (cookie.getName().equals("session")) {
                return cookie;
            }
        }
        return null;
    }

    // tags the tags string and put it into an array
    private ArrayList<String> extractTags(String tags) {

        // probably more efficent ways to do this.
        //
        // whitespace = re.compile('\s')

        tags = tags.replaceAll("\\s", "");
        String tagArray[] = tags.split(",");

        // let's clean it up, removing the empty string and removing dups
        ArrayList<String> cleaned = new ArrayList<String>();
        for (String tag : tagArray) {
            if (!tag.equals("") && !cleaned.contains(tag)) {
                cleaned.add(tag);
            }
        }

        return cleaned;
    }

    // validates that the registration form has been filled out right and username conforms
    public boolean validateSignup(String username, String password, String verify, String email,
                                  HashMap<String, String> errors) {
        String USER_RE = "^[a-zA-Z0-9_-]{3,20}$";
        String PASS_RE = "^.{3,20}$";
        String EMAIL_RE = "^[\\S]+@[\\S]+\\.[\\S]+$";

        errors.put("username_error", "");
        errors.put("password_error", "");
        errors.put("verify_error", "");
        errors.put("email_error", "");

        if (!username.matches(USER_RE)) {
            errors.put("username_error", "invalid username. try just letters and numbers");
            return false;
        }

        if (!password.matches(PASS_RE)) {
            errors.put("password_error", "invalid password.");
            return false;
        }


        if (!password.equals(verify)) {
            errors.put("verify_error", "password must match");
            return false;
        }

        if (!email.equals("")) {
            if (!email.matches(EMAIL_RE)) {
                errors.put("email_error", "Invalid Email Address");
                return false;
            }
        }

        return true;
    }

    private Configuration createFreemarkerConfiguration() {
        Configuration retVal = new Configuration();
        retVal.setClassForTemplateLoading(BlogController.class, "/freemarker");
        return retVal;
    }
}
