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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.util.Helpers.printJson;

public class FinalExam_Q07 {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("photos");
        MongoCollection<Document> collection_images = database.getCollection("images");
        MongoCollection<Document> collection_albums = database.getCollection("albums");

        // Getting all images used in albuns
        List<Document> pipeline = new ArrayList<Document>();
        pipeline.add(new Document("$unwind","$images"));
        List<Document> results = collection_albums.aggregate(pipeline).into(new ArrayList<Document>());
        List<Integer> usedImages = new ArrayList<Integer>();
        
        for (Document cur : results) {
            //printJson(cur);
            usedImages.add(cur.getInteger("images"));
        }
        Collections.sort(usedImages);
        
        
        // Now, for every image check if it is used
        int count_removals = 0;
        for (Document cur : collection_images.find().into(new ArrayList<Document>())) {
            int photo_id = cur.getInteger("_id");
            if (!usedImages.contains(photo_id)) {
            	//System.out.println("Photo_id "+photo_id+" is orphan");
            	count_removals++;
            	collection_images.deleteOne(eq("_id", photo_id));
            }
        }
        System.out.println("Found "+count_removals+" orphans");

        int count_remaining_sunrises = 0;
        for (Document cur : collection_images.find().into(new ArrayList<Document>())) {
        	List<Document> tags = (List<Document>) cur.get("tags");
        	if (tags.contains("sunrises")) {
        		count_remaining_sunrises++;
        	}
        }
        System.out.println("Remained "+count_remaining_sunrises+" sunrise tagged photos");
        
        
    }}
