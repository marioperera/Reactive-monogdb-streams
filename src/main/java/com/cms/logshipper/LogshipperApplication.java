package com.cms.logshipper;

import com.cms.logshipper.Config.MongoConfig;
import com.cms.logshipper.Controllers.MainRunner;
import com.cms.logshipper.Domain.MongoLog;
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

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@SpringBootApplication
public class LogshipperApplication implements CommandLineRunner {

//    @Autowired
//    LogRepository logRepository;

    private Logger logger = LoggerFactory.getLogger(MainRunner.class);

    private  MongoConfig mongoConfig;
    public LogshipperApplication(MongoConfig mongoConfig) {
        this.mongoConfig = mongoConfig;
    }



    public static void main(String[] args) {
        SpringApplication.run(LogshipperApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("------------STARTING LOG-SHIPPER----------------");

        MongoClient client = mongoConfig.mongoClient();
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<MongoLog> collection = database.getCollection("logs", MongoLog.class);
        collection
                .watch(asList(Aggregates.match(Filters.in("operationType", asList("insert", "update", "replace", "delete")))))
                .fullDocument(FullDocument.DEFAULT)
                .forEach( mongoLogChangeStreamDocument -> debug(mongoLogChangeStreamDocument) );


    }

    SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
        @Override
        public void onResult(final Void result, final Throwable t) {
            System.out.println("Operation Finished!");
        }
    };

    void debug(ChangeStreamDocument<MongoLog> log){
        logger.info(" --- logging----");
        logger.info(String.valueOf(log.getDocumentKey().get("_id").asObjectId().getValue()));

    }

    Block<ChangeStreamDocument<Document>> printBlock = new Block<ChangeStreamDocument<Document>>() {
        @Override
        public void apply(ChangeStreamDocument<Document> documentChangeStreamDocument) {
            System.out.println(documentChangeStreamDocument.getFullDocument());
        }
    };
}
