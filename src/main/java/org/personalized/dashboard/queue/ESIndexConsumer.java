package org.personalized.dashboard.queue;

import org.personalized.dashboard.bootstrap.QueueBootstrap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by sudan on 4/7/15.
 */
public class ESIndexConsumer implements MessageListener {

    public ESIndexConsumer() throws JMSException {
        QueueBootstrap.getInstance().getMessageConsumer().setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
    }
}
