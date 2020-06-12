package com.cms.logshipper.Controllers;

import com.cms.logshipper.Config.MongoConfig;
import com.cms.logshipper.Domain.MongoLog;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.function.Consumer;


public class MainRunner implements ApplicationRunner {
    @Autowired
    MongoConfig mongoConfig;

    Logger logger = LoggerFactory.getLogger(MainRunner.class);



    private void log(){
        logger.info("document added");
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
