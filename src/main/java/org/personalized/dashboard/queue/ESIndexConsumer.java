package org.personalized.dashboard.queue;

import com.google.gson.Gson;
import org.personalized.dashboard.bootstrap.QueueBootstrap;
import org.personalized.dashboard.elasticsearch.ElasticsearchClient;
import org.personalized.dashboard.model.ESDocument;
import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.model.OperationType;
import org.personalized.dashboard.utils.FieldKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * Created by sudan on 4/7/15.
 */
public class ESIndexConsumer implements MessageListener {

    private final Logger LOGGER = LoggerFactory.getLogger(ESIndexConsumer.class);

    private ElasticsearchClient elasticsearchClient = new ElasticsearchClient();

    public ESIndexConsumer() throws JMSException {
        QueueBootstrap.getInstance().getMessageConsumer().setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        if (message != null) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String payload = textMessage.getText();
                Map<String, String> payloadMap = new Gson().fromJson(payload, Map.class);

                if(OperationType.valueOf(payloadMap.get(FieldKeys.ES_OP_TYPE)) == OperationType.DELETE) {
                    elasticsearchClient.delete(payloadMap.get(FieldKeys.ES_ID));
                }
                else {
                    ESDocument esDocument = new ESDocument();
                    esDocument.setDocumentId(payloadMap.get(FieldKeys.ES_ID));
                    esDocument.setTitle(payloadMap.get(FieldKeys.ES_TITLE));
                    esDocument.setDescription(payloadMap.get(FieldKeys.ES_DESCRIPTION));
                    esDocument.setEntityType(EntityType.valueOf(payloadMap.get(FieldKeys.ES_ENTITY_TYPE)));
                    elasticsearchClient.insert(esDocument);
                }

            } catch (JMSException e) {
                LOGGER.error("Error in consuming message ", textMessage);
            }
        }
    }
}
