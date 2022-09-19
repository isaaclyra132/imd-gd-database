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
import static com.mongodb.util.Helpers.printJson;

public class Homework_2_3 {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");

        Bson sort = orderBy(ascending("student_id"),ascending("score"));
        
        Bson filter = and(eq("type", "homework"));
        
        List<Document> all = collection.find(filter)
                .sort(sort)
                .into(new ArrayList<Document>());

        int lastDeleted = -1;
        int count_deleted = 0;
        for (Document cur : all) {
            int id = cur.getInteger("student_id");
            double score = cur.getDouble("score");            		
            if (lastDeleted != id) {
                collection.deleteOne(and(eq("student_id", id), eq("score",score)));
                lastDeleted = id;
                count_deleted++;
            }
            
        }
        System.out.println("deleted "+count_deleted);
        
        /*
        int count = 0;
        for (Document cur : all) {
            printJson(cur);
            count++;
        }
        System.out.println("count "+count);
        */
        
        
    }}
