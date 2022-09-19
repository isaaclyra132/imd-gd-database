package com.mongodb.crud;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class IntroToMongoDBDriver 
{
    public static void main( String[] args )
    {
    	// Use MongoClientOptions if you want to change anything in the client
    	// One example is connections per host - default is a 100
    	MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(10).build();
    	
    	// Default is localhost at port 27017
    	// MongoClient client = new MongoClient();

    	// Using URI
    	// MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

    	// Explicit ServerAddress
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017), options);
        
        MongoDatabase db = client.getDatabase("test");
        
        // You may want to set preferences. Make sure you assign it to a new variable because
        // MongoDatabase in immutable
        MongoDatabase db_with_preference = db.withReadPreference(ReadPreference.secondary());
        
        MongoCollection<Document> collection = db.getCollection("test");
        // You may set preferences in the collection as well. Again, MongoCollection is Imutable
        MongoCollection<Document> collection_with_preference = collection.withReadPreference(ReadPreference.secondary());
        	
    }
}
