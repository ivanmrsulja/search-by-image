package com.searchbyimage.searchservice.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.searchbyimage.searchservice.elasticsearchrepository")
public class ElasticSearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.username}")
    private String userName;

    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public ElasticsearchClient restClient() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials(userName, password));

        RestClient client = RestClient.builder(new HttpHost(host, port, "http"))
            .setHttpClientConfigCallback(
                httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(
                    credentialsProvider))
            .setDefaultHeaders(compatibilityHeaders()).build();

        JacksonJsonpMapper jsonMapper = new JacksonJsonpMapper();

        ElasticsearchTransport transport = new RestClientTransport(client, jsonMapper);

        return new ElasticsearchClient(transport);
    }

    private Header[] compatibilityHeaders() {
        return new Header[] {new BasicHeader(HttpHeaders.ACCEPT,
            "application/vnd.elasticsearch+json;compatible-with=7"),
            new BasicHeader(HttpHeaders.CONTENT_TYPE,
                "application/vnd.elasticsearch+json;compatible-with=7")};
    }
}
