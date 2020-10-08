package com.samples.kafka.streams.serdes.model;


public class RetryMessageKey {

    //TODO : make one property of 2.4.0 of kafka-streams/kafka-clients
    private String entity;
    private String entityId;

    public RetryMessageKey(String entity, String entityId) {
        this.entity = entity;
        this.entityId = entityId;
    }

    public static RetryMessageKey.RetryMessageKeyBuilder builder() {
        return new RetryMessageKey.RetryMessageKeyBuilder();
    }

    public static class RetryMessageKeyBuilder {
        private String entity;
        private String entityId;

        public RetryMessageKeyBuilder entity(String entity) {
            this.entity = entity;
            return this;
        }

        public RetryMessageKeyBuilder entityId(String entityId) {
            this.entityId = entityId;
            return this;
        }

        public RetryMessageKey build() {
            return new RetryMessageKey(entity, entityId);
        }
    }
}

