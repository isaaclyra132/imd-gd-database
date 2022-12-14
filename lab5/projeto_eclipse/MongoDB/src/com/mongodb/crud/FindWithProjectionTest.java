/*
 * Copyright 2015 MongoDB, Inc.
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
 */

package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.util.Helpers.printJson;

public class FindWithProjectionTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithFilterTest");

        collection.drop();

        // insert 10 documents with two random integers
        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                                 .append("x", new Random().nextInt(2))
                                 .append("y", new Random().nextInt(100))
                                 .append("i", i));
        }

        // We have statically imported 
        // com.mongodb.client.model.Filters.and;
        // com.mongodb.client.model.Filters.eq;
        // com.mongodb.client.model.Filters.gt;
        // com.mongodb.client.model.Filters.lt;
        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

        // Test (1)
        /*
        List<Document> all = collection.find(filter).into(new ArrayList<Document>());
        for (Document cur : all) {
            printJson(cur);
        }
        */


        // Test (2)
        /*
        Bson projection = new Document("x", 0);
        List<Document> all = collection.find(filter)
        								.projection(projection)
        								.into(new ArrayList<Document>());
        for (Document cur : all) {
            printJson(cur);
        }
        */

        // Test (3)
        /*
        Bson projection = new Document("x", 0).append("_id", 0);
        List<Document> all = collection.find(filter)
        								.projection(projection)
        								.into(new ArrayList<Document>());
        for (Document cur : all) {
            printJson(cur);
        }
        */

        // Test (4)
        /*
        Bson projection = new Document("y", 1).append("i", 1).append("_id", 0);
        List<Document> all = collection.find(filter)
        								.projection(projection)
        								.into(new ArrayList<Document>());
        for (Document cur : all) {
            printJson(cur);
        }
        */

        // Test (5) - As with Filter, you can use a raw document or a builder, in this case Projections
        // We have statically imported
        // com.mongodb.client.model.Projections.exclude;
        // com.mongodb.client.model.Projections.excludeId;
        // com.mongodb.client.model.Projections.fields;
        // com.mongodb.client.model.Projections.include;
        
        /*
        Bson projection = exclude("x", "_id");
        List<Document> all = collection.find(filter)
        								.projection(projection)
        								.into(new ArrayList<Document>());
        for (Document cur : all) {
            printJson(cur);
        }
        */
        
        // Test (6)
        // We have statically imported com.mongodb.client.model.Projections.include
        /*
        Bson projection = include("y", "i");
        List<Document> all = collection.find(filter)
        								.projection(projection)
        								.into(new ArrayList<Document>());
        for (Document cur : all) {
            printJson(cur);
        }
        */

        
        // Test (7) - Combining exclude and include
        //Bson projection = fields(include("y", "i"), exclude("_id"));
        
        /*
        Bson projection = fields(include("y", "i"), excludeId());

        List<Document> all = collection.find(filter)
                                       .projection(projection)
                                       .into(new ArrayList<Document>());

        for (Document cur : all) {
            printJson(cur);
        }
		*/
        
    }
}
