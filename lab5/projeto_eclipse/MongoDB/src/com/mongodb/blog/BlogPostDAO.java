package com.mongodb.blog;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.util.Helpers.printJson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {
        Document post = postsCollection.find(eq("permalink", permalink)).first();

        // fix up if a post has no likes
        if (post != null) {
            List<Document> comments = (List<Document>) post.get("comments");
            for (Document comment : comments) {
                if (!comment.containsKey("num_likes")) {
                    comment.put("num_likes", 0);
                }
            }
        }
        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {

        // Return a list of DBObjects, each one a post from the posts collection
        List<Document> posts = null;

        Bson sort = orderBy(descending("date"));

        posts = postsCollection.find().sort(sort)
                                      .limit(limit)
                                      .into(new ArrayList<Document>());
        
        return posts;
    }

    public List<Document> findByTagDateDescending(final String tag) {
        return postsCollection.find(Filters.eq("tags", tag))
                       .sort(Sorts.descending("date"))
                       .limit(10)
                       .into(new ArrayList<Document>());
    }

    
    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();


        // XXX HW 3.2, Work Here
        // Remember that a valid post has the following keys:
        // author, body, permalink, tags, comments, date, title
        //
        // A few hints:
        // - Don't forget to create an empty list of comments
        // - for the value of the date key, today's datetime is fine.
        // - tags are already in list form that implements suitable interface.
        // - we created the permalink for you above.

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        // Build the post object and insert it
        Document post = new Document("title", title)
        					 .append("author", username)
        					 .append("body", body)
        					 .append("permalink", permalink)
        					 .append("tags", tags)
        					 .append("comments", new ArrayList())
        					 .append("date", date);
        
        postsCollection.insertOne(post);

        return permalink;
    }

    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        //   operator to append the comment on to any existing list of comments

        Document comment = new Document("author", name)
		 .append("body", body);
        if (email != null) {
   		 	comment.append("email", email);
        }
        
        postsCollection.updateMany(eq("permalink",permalink), Updates.addToSet("comments", comment) );
    	
    }
    
    public void likePost(final String permalink, final int ordinal) {
        //
        //
        // XXX Final Question 4 - work here
        // You must increment the number of likes on the comment in position `ordinal`
        // on the post identified by `permalink`.
        //
        //
        postsCollection.updateMany(eq("permalink",permalink), 
        							Updates.inc("comments."+ordinal+".num_likes",1));
    }
    
}
