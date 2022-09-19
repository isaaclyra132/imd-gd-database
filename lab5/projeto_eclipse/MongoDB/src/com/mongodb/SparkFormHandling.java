/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mongodb;

import static spark.Spark.get;
import static spark.Spark.port;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;
import static spark.Spark.*;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SparkFormHandling {
    public static void main(String[] args) {
        // Configure Freemarker
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(SparkFormHandling.class, "/freemarker");


        StringWriter writer = new StringWriter();
        try {
            Map<String, Object> fruitsMap = new HashMap<String, Object>();
            fruitsMap.put("fruits",
                    Arrays.asList("apple", "orange", "banana", "peach"));

            Template fruitPickerTemplate =
                    configuration.getTemplate("fruitPicker.ftl");
            fruitPickerTemplate.process(fruitsMap, writer);
        } catch (Exception e) {
            Spark.halt(500);
        }

        //port(8080);
        get("/", (req, res) -> {return writer.toString();});
        

        post("/favorite_fruit", (req, res) -> {
        	String answer = "Why don't you pick one?";
            if (req.queryParams("fruit") != null) {
            	answer = "Your favorite fruit is " + req.queryParams("fruit");
            }
            return answer; 
        });

    }
    
}
