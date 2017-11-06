/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.ibv3;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 *
 * @author Apple
 */
public class MongoUtils {
    private MongoClient mongoClient = null;
    private static MongoUtils connectionInstance = null;
    
    private MongoUtils() {
    }
    
    public static MongoUtils createConnectionInstance() {
        if (connectionInstance == null) {
            connectionInstance = new MongoUtils();
        }
        return connectionInstance;
    }
    
    public void writeToDb(String[] bar) {
        try {
            if (mongoClient == null) 
                mongoClient = new MongoClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        DB database = mongoClient.getDB("myMongoDB");
        database.createCollection("EURUSD1min", null);
        
        DBCollection collection = database.getCollection("EURUSD1min");
        BasicDBObject document = new BasicDBObject();
        document.put("timestamp", bar[0]);
        document.put("open", bar[1]);
        document.put("high", bar[2]);
        document.put("low", bar[3]);
        document.put("close", bar[4]);
        collection.insert(document);
    }
    
    public void queryFromDb() {
        try {
            if (mongoClient == null) 
                mongoClient = new MongoClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        DB database = mongoClient.getDB("myMongoDB");
        database.createCollection("EURUSD1min", null);
        DBCollection collection = database.getCollection("EURUSD1min");
        BasicDBObject searchQuery = new BasicDBObject();
        DBCursor cursor = collection.find(searchQuery);
        
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}