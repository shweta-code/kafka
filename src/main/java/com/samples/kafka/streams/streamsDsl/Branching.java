package com.samples.kafka.streams.streamsDsl;

import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.TimeWindows;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Branching {


    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-dsl-branch");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "0.0.0.0:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final KafkaStreams streams = new KafkaStreams(getTopology(), props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }

    protected static Topology getTopology() {
        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, String>[] branchedStream = builder.stream("streams-branch-input",
                Consumed.with(Serdes.String(), Serdes.String()))
                .branch((key, value) -> value.equals("PARTICULAR"), (key, value) -> !value.equals("PARTICULAR"));

        branchedStream[0].to("particular-topic");
        branchedStream[1].to("not-particular-topic");
        return builder.build();
    }
}
