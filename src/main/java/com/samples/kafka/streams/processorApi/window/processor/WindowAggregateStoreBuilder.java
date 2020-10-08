package com.samples.kafka.streams.processorApi.window.processor;

import com.samples.kafka.streams.processorApi.window.StateStoreNames;
import com.samples.kafka.streams.processorApi.window.StoreBuilderProvider;
import com.samples.kafka.streams.serdes.SerdeRegistry;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.TimestampedWindowStore;

import java.time.Duration;

public class WindowAggregateStoreBuilder implements StoreBuilderProvider<TimestampedWindowStore<String, String>> {

    private final SerdeRegistry serdeRegistry;

    public WindowAggregateStoreBuilder(SerdeRegistry serdeRegistry) {
        this.serdeRegistry = serdeRegistry;
    }


    @Override
    public StoreBuilder<TimestampedWindowStore<String, String>> get() {
        //Why should we enable cache? -- https://docs.confluent.io/current/streams/developer-guide/memory-mgmt.html
        return Stores.timestampedWindowStoreBuilder(
                Stores.persistentTimestampedWindowStore(StateStoreNames.WINDOW, Duration.ofDays(1), Duration.ofMillis(120000), Boolean.FALSE),
                Serdes.String(),
                Serdes.String())
                .withCachingEnabled();
    }
}
