package com.samples.kafka.streams.serdes.model;


public class RetryMessageValue {

    private Long timestamp;

    public RetryMessageValue(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public static RetryMessageValue.RetryMessageValueBuilder builder() {
        return new RetryMessageValue.RetryMessageValueBuilder();
    }

    public static class RetryMessageValueBuilder {
        private Long timestamp;

        public RetryMessageValueBuilder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public RetryMessageValue build() {
            return new RetryMessageValue(timestamp);
        }
    }
}
