package org.personalized.dashboard.queue;

import org.personalized.dashboard.bootstrap.QueueBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by sudan on 4/7/15.
 */
public class ESIndexConsumer implements MessageListener {

    private final Logger LOGGER = LoggerFactory.getLogger(ESIndexConsumer.class);

    public ESIndexConsumer() throws JMSException {
        QueueBootstrap.getInstance().getMessageConsumer().setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        if (message != null) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String payload = textMessage.getText();
            } catch (JMSException e) {
                LOGGER.error("Error in consuming message ", textMessage);
            }
        }
    }
}
