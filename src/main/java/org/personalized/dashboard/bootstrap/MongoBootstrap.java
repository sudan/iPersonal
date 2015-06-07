package org.personalized.dashboard.bootstrap;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

/**
 * Created by sudan on 11/5/15.
 */
public class MongoBootstrap {

    private static final MongoDatabase mongoDatabase = MongoBootstrap.init();

    private MongoBootstrap() {

    }

    public static MongoDatabase init() {
        if (mongoDatabase == null) {
            String username = ConfigManager.getValue("mongo.username");
            String password = ConfigManager.getValue("mongo.password");
            String dbName = ConfigManager.getValue("mongo.dbName");
            String hostName = ConfigManager.getValue("mongo.hostName");
            String portNumber = ConfigManager.getValue("mongo.portNumber");
            MongoCredential mongoCredential = MongoCredential.createCredential(username, dbName, password.toCharArray());
            MongoClient mongoClient = new MongoClient(new ServerAddress(hostName, Integer.parseInt(portNumber)), Arrays.asList(mongoCredential));
            return mongoClient.getDatabase(dbName);
        }
        return mongoDatabase;
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

}
