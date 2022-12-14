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
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.util.Helpers.printJson;

public class FindWithSortSkipLimitTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findWithSortTest");

        collection.drop();

        // insert 100 documents with two integers
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                collection.insertOne(new Document().append("i", i).append("j", j));
            }
        }

        Bson projection = fields(include("i", "j"), excludeId());
        
        // Test (1)
        //Bson sort = new Document("i", 1);
        
        // Test (2)
        //Bson sort = new Document("i", 1).append("j",1);
        
        // Test (3)
        //Bson sort = new Document("i", 1).append("j",-1);
        
        // Test (4)
        // We have statically imported
        // com.mongodb.client.model.Sorts.ascending;
        // com.mongodb.client.model.Sorts.descending;
        // com.mongodb.client.model.Sorts.orderBy;
        //Bson sort = ascending("i");

        // Test (5)
        //Bson sort = ascending("i","j");

        // Test (6)
        Bson sort = orderBy(ascending("i"),descending("j"));

        // Limits the first 50 results, but skips the first 20 results
        List<Document> all = collection.find()
                                       .projection(projection)
                                       .sort(sort)
                                       .skip(20)
                                       .limit(50)
                                       .into(new ArrayList<Document>());

        for (Document cur : all) {
            printJson(cur);
        }
    }
}
