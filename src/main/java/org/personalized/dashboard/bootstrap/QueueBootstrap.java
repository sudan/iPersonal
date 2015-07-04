package org.personalized.dashboard.bootstrap;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by sudan on 4/7/15.
 */
public class QueueBootstrap {

    private  static MessageProducer messageProducer;
    private  static MessageConsumer messageConsumer;

    private static boolean isInitialized = false;

    public QueueBootstrap() {

    }

    public static  void init() throws JMSException{
        if(!isInitialized) {

            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory
                    (
                            ConfigManager.getValue("activemq.username"),
                            ConfigManager.getValue("activemq.password"),
                            ConfigManager.getValue("activemq.url")
                    );
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(ConfigManager.getValue("activemq.destQueue"));
            messageProducer = session.createProducer(destination);
            messageConsumer = session.createConsumer(destination);
            isInitialized = true;
        }
    }

    public MessageProducer getMessageProducer()  {
        return messageProducer;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

}
