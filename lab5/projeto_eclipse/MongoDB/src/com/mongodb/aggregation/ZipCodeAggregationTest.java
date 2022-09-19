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

package com.mongodb.aggregation;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.util.Helpers.printJson;

public class ZipCodeAggregationTest {
    public static void main(String[] args) {
    	
    	// To run this test, you must have the data collection
    	// zipcodes.zips
    	// If it does no exist, import it using
    	// - In the prompt, go the the data/zips.js directory
    	// - execute mongoimport --drop --file zips.json --db zipcode --collection zips
    	
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("zipcode");
        MongoCollection<Document> collection = database.getCollection("zips");

        //List<Document> results = collection.find().into(new ArrayList<Document>());

        /*
         db.zipcodes.aggregate( [
   				{ $group: { _id: "$state", totalPop: { $sum: "$pop" } } },
   				{ $match: { totalPop: { $gte: 10000000 } } }
		 ] )
        */

        List<Document> pipeline = new ArrayList<Document>();
        pipeline.add(new Document("$group", new Document("_id","$state").append("totalPop", new Document("$sum","$pop"))));
        pipeline.add(new Document("$match", new Document("totalPop", new Document("$gte", 10000000))));
        List<Document> results = collection.aggregate(pipeline).into(new ArrayList<Document>());

        for (Document cur : results) {
            printJson(cur);
        }
    }
}
