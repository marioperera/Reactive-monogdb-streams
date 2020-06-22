package com.cms.logshipper.ElasticConnectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class MainRunner  {
    @Value("${elasticsearch.url}")
    private String url;

    RestTemplate restTemplate = new RestTemplate();


    private Logger logger = LoggerFactory.getLogger(MainRunner.class);

    public void indexDocument(HashMap<String, Object> log){


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.remove("_id");

        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat DateFortmspt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate= DateFor.format(date);
        String timestamp =log.get("TIMESTAMP").toString();
        Date datenew=null;
        try {
            datenew = DateFortmspt.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.info(stringDate);
        logger.info(DateFor.format(datenew));
        log.put("DATE",stringDate);
        log.put("TIMESTAMP",DateFor.format(datenew));
        String json ="";
        try{

            json = new ObjectMapper().writeValueAsString(log);
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        String response = "default response";
        try {
            String uniqueid = (String) log.get("UNIQUE_ID");
            logger.info(uniqueid);

            restTemplate.postForLocation(url+uniqueid,requestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(response);
    }

    private void printsuccess(){
        logger.info("Success!!");
    }


}
