package com.odk.elasticsearchspringbootstarter;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * ElasticSearchConfig
 * api文档：https://docs.spring.io/spring-data/elasticsearch/docs/4.1.2/reference/html/#elasticsearch.clients.rest
 *
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@SpringBootConfiguration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${spring.odk.elasticsearch.host-and-port}")
    private String hostAndPort;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(hostAndPort)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
