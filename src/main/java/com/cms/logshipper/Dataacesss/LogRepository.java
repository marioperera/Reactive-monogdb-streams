package com.cms.logshipper.Dataacesss;

import com.cms.logshipper.Domain.LogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogDocument,String> {
}
