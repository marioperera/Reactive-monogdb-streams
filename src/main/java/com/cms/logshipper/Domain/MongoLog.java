package com.cms.logshipper.Domain;

import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class MongoLog extends Document {


    private ObjectId _id;
//    @BsonProperty(value = "CLIENT_IP")
    String CLIENT_IP;
//    @BsonProperty(value = "REQUESTED_SERVISE")
    String REQUESTED_SERVISE;
//    @BsonProperty(value = "REQUEST_TYPE")
    String REQUEST_TYPE;

//    @BsonProperty(value = "AUTH_HEADER")
    String AUTH_HEADER;

//    @BsonProperty(value = "PARAMETERS")
    String PARAMETERS;
//    @BsonProperty(value = "TIMESTAMP")

    String TIMESTAMP;
//    @BsonProperty(value = "RESPONSE_CODE")

    String RESPONSE_CODE;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getCLIENT_IP() {
        return CLIENT_IP;
    }

    public void setCLIENT_IP(String CLIENT_IP) {
        this.CLIENT_IP = CLIENT_IP;
    }

    public String getREQUESTED_SERVISE() {
        return REQUESTED_SERVISE;
    }

    public void setREQUESTED_SERVISE(String REQUESTED_SERVISE) {
        this.REQUESTED_SERVISE = REQUESTED_SERVISE;
    }

    public String getREQUEST_TYPE() {
        return REQUEST_TYPE;
    }

    public void setREQUEST_TYPE(String REQUEST_TYPE) {
        this.REQUEST_TYPE = REQUEST_TYPE;
    }

    public String getAUTH_HEADER() {
        return AUTH_HEADER;
    }

    public void setAUTH_HEADER(String AUTH_HEADER) {
        this.AUTH_HEADER = AUTH_HEADER;
    }

    public String getPARAMETERS() {
        return PARAMETERS;
    }

    public void setPARAMETERS(String PARAMETERS) {
        this.PARAMETERS = PARAMETERS;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public String getRESPONSE_CODE() {
        return RESPONSE_CODE;
    }

    public void setRESPONSE_CODE(String RESPONSE_CODE) {
        this.RESPONSE_CODE = RESPONSE_CODE;
    }
}
