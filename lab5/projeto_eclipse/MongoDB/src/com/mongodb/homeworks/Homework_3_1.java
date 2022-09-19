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

package com.mongodb.homeworks;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.unset;
import static com.mongodb.util.Helpers.printJson;

public class Homework_3_1 {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");

        List<Document> all = collection.find().into(new ArrayList<Document>());

        for (Document cur : all) {
            int id = cur.getInteger("_id");
            String name = cur.getString("name");
            System.out.println("_id = "+id+" name = "+name);

            List<Document> scores = (List<Document>) cur.get("scores");
            
            double lowest_score = 100;
            int lowest_index = -1;
            
            for (int i = 0; i<scores.size(); i++) {
            	Document score = scores.get(i);
                String type = score.getString("type");
                double value = score.getDouble("score");
                
                if (type.equals("homework") && value < lowest_score) {
                	lowest_score = value;
                	lowest_index = i;
                }
            }
            if (lowest_index >= 0) {
                System.out.println("lowest score = "+lowest_score+ " at "+lowest_index);
                System.out.println("old score array size = "+scores.size());
                System.out.println("removing index = "+lowest_index);
            	scores.remove(lowest_index);
                System.out.println("new score array size = "+scores.size());
            	
                collection.updateOne(eq("_id", id),unset("scores"));
                
                
                collection.updateOne(eq("_id", id),set("scores", scores));
            }
        }
    }}
