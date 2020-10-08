package com.samples.kafka.streams.processorApi.branch;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Branching {


    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-window");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "0.0.0.0:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final Topology topology = buildTopology();
        final KafkaStreams streams = new KafkaStreams(topology, props);
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

    private static Topology buildTopology() {
        Topology builder = new Topology();
        builder
                .addSource("source-processor", Serdes.String().deserializer(), Serdes.String().deserializer(), "process-branch-input")
                .addProcessor("branching-processor", new BranchingProcessorSupplier(), "source-processor")
                .addSink("particular-processor", "particular-topic" , "branching-processor")
                .addSink("non-particular-processor", "not-particular-topic" , "branching-processor");
        return builder;

    }
}
