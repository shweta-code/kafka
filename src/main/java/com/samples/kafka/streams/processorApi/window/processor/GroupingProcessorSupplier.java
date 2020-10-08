package com.samples.kafka.streams.processorApi.window.processor;

import com.samples.kafka.streams.processorApi.window.StateStoreNames;
import com.samples.kafka.streams.utils.JsonUtils;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.internals.KStreamWindowAggregate;
import org.apache.kafka.streams.kstream.internals.TimeWindow;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class GroupingProcessorSupplier implements ProcessorSupplier<String, String> {

    private final KStreamWindowAggregate<String, String, String, TimeWindow> processorSupplier;

    public GroupingProcessorSupplier() {
        processorSupplier =
                new KStreamWindowAggregate<>(
                        TimeWindows.of(Duration.ofMillis(120000)),
                        StateStoreNames.WINDOW,
                        () -> "",
                        (key, newValue, oldValue) -> {
                            //if old aggregate is null , it will take value from initializer
                            return newValue + " " + oldValue;

                        });
    }

    @Override
    public Processor<String, String> get() {
        return processorSupplier.get();
    }
}
