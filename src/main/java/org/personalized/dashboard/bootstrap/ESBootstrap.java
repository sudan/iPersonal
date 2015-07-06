package org.personalized.dashboard.bootstrap;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * Created by sudan on 5/7/15.
 */
public class ESBootstrap {

    private static Client elasticSearchClient;
    private static boolean isInitialized = false;

    public static void init() {

        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", ConfigManager.getValue("elasticsearch.clustername"))
                .put("node.name", ConfigManager.getValue("elasticsearch.nodename")).build();
        if (!isInitialized) {
            elasticSearchClient = new TransportClient(settings)
                    .addTransportAddress(
                            new InetSocketTransportAddress
                                    (
                                            ConfigManager.getValue("elasticsearch.url"),
                                            Integer.parseInt(ConfigManager.getValue("elasticsearch.port"))
                                    )
                    );
            isInitialized = true;
        }
    }

    public static Client getClient() {
        return elasticSearchClient;
    }
}
