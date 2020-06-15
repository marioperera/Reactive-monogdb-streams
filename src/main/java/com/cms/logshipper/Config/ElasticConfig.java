package com.cms.logshipper.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class ElasticConfig  {
    @Value("${elasticsearch.url}")
    private String url;

    @Bean
    public WebClient getClient(){

        return WebClient.create(url);

    }



}
