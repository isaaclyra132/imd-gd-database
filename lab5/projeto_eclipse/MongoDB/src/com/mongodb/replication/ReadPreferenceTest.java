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

package com.mongodb.replication;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class ReadPreferenceTest {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        MongoClient client = new MongoClient(asList(new ServerAddress("localhost", 27018),
                                                    new ServerAddress("localhost", 27019),
                                                    new ServerAddress("localhost", 27020)));

        MongoDatabase database = client.getDatabase("course").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = database.getCollection("replication");

        List<Document> documents = collection.withReadPreference(ReadPreference.primaryPreferred()).find().into(new ArrayList<Document>());

        for (Document doc : documents) {
            System.out.println(doc.toJson());
        }
    }
}
