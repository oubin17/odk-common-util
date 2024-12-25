package com.odk.elasticsearchspringbootstarter;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

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
public class ElasticSearchConfig {

    @Value("${spring.odk.elasticsearch.host-and-port}")
    private String hostAndPort;

//
//    @Bean
//    public RestClient restClient() {
//        // 配置身份验证信息
//        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                new AuthScope("localhost", 9200), // ES主机和端口
//                new UsernamePasswordCredentials("elastic", "JhkceRsFZRsTJcKjFkaK") // 用户名和密码
//        );
//
//        return RestClient.builder(new HttpHost("localhost", 9200))
//                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
//                .build();
//    }

//
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchRestTemplate(RestClient restClient) {
//        // 使用 ClientConfiguration 配置 Elasticsearch 连接
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200") // 配置连接到 Elasticsearch 的主机
//                .usingHttpClient(restClient) // 使用自定义的 RestClient
//                .build();
//
//        return new ElasticsearchRestTemplate(RestClients.create(clientConfiguration).rest());
//    }
//
//
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchRestTemplate(RestClient restClient) {
//        // 配置 ClientConfiguration
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200")  // 配置连接到 Elasticsearch 的主机
//                .withBasicAuth("elastic", "JhkceRsFZRsTJcKjFkaK")
////                .withRestClient(restClient)  // 使用自定义的 RestClient
//                .build();
//
//        return new ElasticsearchRestTemplate(RestClients.create(clientConfiguration).rest());
//
//    }

//
//    @Bean
//    public RestClient restClient() throws Exception {
//        // 配置 IOReactor
//        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
//                .setIoThreadCount(Runtime.getRuntime().availableProcessors() * 2)  // 设置 IO 线程数
//                .setConnectTimeout(1000)  // 连接超时
//                .setSoTimeout(1000)  // 套接字超时
//                .build();
//
//        // 创建连接池
//        PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(
//                new DefaultConnectingIOReactor(ioReactorConfig));
//        connectionManager.setDefaultMaxPerRoute(10);  // 每路由最大连接数
//        connectionManager.setMaxTotal(30);  // 总连接数限制
//
//        // 配置 RestClientBuilder
//        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"))
//                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
//                        httpAsyncClientBuilder.setConnectionManager(connectionManager));
//
//        // 返回自定义的 RestClient
//        return builder.build();
//    }

//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate(ElasticsearchClient client, ElasticsearchConverter elasticsearchConverter) {
//        return new ElasticsearchTemplate(client, elasticsearchConverter);
//    }
}
