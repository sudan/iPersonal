package org.personalized.dashboard.utils;

import org.personalized.dashboard.bootstrap.ConfigManager;

/**
 * Created by sudan on 11/7/15.
 */
public class ConfigKeys {

    public static final String MONGO_USERNAME = ConfigManager.getValue("mongo.username");
    public static final String MONGO_PASSWORD = ConfigManager.getValue("mongo.password");
    public static final String MONGO_DBNAME = ConfigManager.getValue("mongo.dbName");
    public static final String MONGO_HOSTNAME = ConfigManager.getValue("mongo.hostName");
    public static final String MONGO_PORTNUMBER = ConfigManager.getValue("mongo.portNumber");


    public static final String QUEUE_USERNAME = ConfigManager.getValue("activemq.username");
    public static final String QUEUE_PASSWORD = ConfigManager.getValue("activemq.password");
    public static final String QUEUE_URL = ConfigManager.getValue("activemq.url");
    public static final String QUEUE_DEST = ConfigManager.getValue("activemq.destQueue");

    public static final String ES_CLUSTERNAME = ConfigManager.getValue("elasticsearch.clustername");
    public static final String ES_NODENAME = ConfigManager.getValue("elasticsearch.nodename");
    public static final String ES_URL = ConfigManager.getValue("elasticsearch.url");
    public static final String ES_PORT = ConfigManager.getValue("elasticsearch.port");
    public static final String ES_INDEX = ConfigManager.getValue("elasticsearch.index");
    public static final String ES_TYPE = ConfigManager.getValue("elasticsearch.type");
}
