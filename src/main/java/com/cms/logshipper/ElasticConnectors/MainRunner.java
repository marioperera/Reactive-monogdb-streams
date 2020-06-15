package com.cms.logshipper.ElasticConnectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class MainRunner  {
    @Value("${elasticsearch.url}")
    private String url;


    private Logger logger = LoggerFactory.getLogger(MainRunner.class);

    public void indexDocument(HashMap<String, String> log){
        WebClient webClient = WebClient.create("http://localhost:9200");
        try{
            WebClient.ResponseSpec response = webClient
                    .post()
                    .uri("logs/log/"+log.get("CLIENT_IP"))
                    .body(Mono.just(log),HashMap.class)
                    .retrieve();
            Mono<String> resp = response.bodyToMono(String.class);
            logger.info(resp.block());
        }catch (Exception we){
            logger.error(we.getMessage());
        }
        printsuccess();
    }

    private void printsuccess(){
        logger.info("Success!!");
    }


}
