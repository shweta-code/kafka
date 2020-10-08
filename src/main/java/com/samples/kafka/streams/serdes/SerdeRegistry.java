package com.samples.kafka.streams.serdes;


import com.samples.kafka.streams.serdes.model.RetryMessageKey;

public class SerdeRegistry {
    private final RetryMessageKeySerde retryMessageKeySerde;

    public SerdeRegistry() {
        this.retryMessageKeySerde = new RetryMessageKeySerde();
    }

    public static final class RetryMessageKeySerde extends WrapperSerde<RetryMessageKey> {
        private RetryMessageKeySerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>(RetryMessageKey.class));
        }
    }

    public RetryMessageKeySerde getRetryMessageKeySerde() {
        return retryMessageKeySerde;
    }
}