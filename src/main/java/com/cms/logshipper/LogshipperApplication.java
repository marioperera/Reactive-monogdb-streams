package com.cms.logshipper;

import com.cms.logshipper.Config.MongoConfig;
import com.cms.logshipper.ElasticConnectors.MainRunner;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import com.mongodb.internal.async.SingleResultCallback;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashMap;

@SpringBootApplication
public class LogshipperApplication implements CommandLineRunner {


    private Logger logger = LoggerFactory.getLogger(MainRunner.class);
    private MainRunner runner;
    private  MongoConfig mongoConfig;

    public LogshipperApplication(MongoConfig mongoConfig, MainRunner mainrunner) {
        this.runner = mainrunner;
        this.mongoConfig = mongoConfig;
    }



    public static void main(String[] args) {
        SpringApplication.run(LogshipperApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("------------STARTING LOG-SHIPPER----------------");

        MongoClient client = mongoConfig.mongoClient();
        MongoDatabase database = client.getDatabase("epiccmsapi");
        MongoCollection<HashMap> collection = database.getCollection("REQUESTLOG", HashMap.class);
        try{
            collection
                    .watch()
                    .fullDocument(FullDocument.DEFAULT)
                    .forEach(this::debug);
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
        @Override
        public void onResult(final Void result, final Throwable t) {
            System.out.println("Operation Finished!");
        }
    };

    void debug(ChangeStreamDocument<HashMap> log){
        logger.info(" --- logging----");
        assert log.getDocumentKey() != null;
        logger.info(String.valueOf(log.getDocumentKey().get("_id").asObjectId().getValue()));
        HashMap lg = new HashMap<>();
        try{
            lg = log.getFullDocument();
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info(" --- Calling Elastic server----");
        HashMap<String, Object> doc = log.getFullDocument();
        doc.put("UNIQUE_ID",String.valueOf(log.getDocumentKey().get("_id").asObjectId().getValue()));
        logger.info(doc.toString());
        this.runner.indexDocument(doc);

    }

    Block<ChangeStreamDocument<Document>> printBlock = new Block<ChangeStreamDocument<Document>>() {
        @Override
        public void apply(ChangeStreamDocument<Document> documentChangeStreamDocument) {
            System.out.println(documentChangeStreamDocument.getFullDocument());
        }
    };
}
