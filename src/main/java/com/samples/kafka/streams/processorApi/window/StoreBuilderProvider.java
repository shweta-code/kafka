package com.samples.kafka.streams.processorApi.window;

import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.StoreBuilder;

public interface StoreBuilderProvider<T extends StateStore> {

    StoreBuilder<T> get();
}
