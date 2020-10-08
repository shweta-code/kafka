package com.samples.kafka.streams.processorApi.branch;
;

import org.apache.kafka.streams.processor.ProcessorSupplier;

import java.util.Arrays;

public class BranchingProcessorSupplier implements ProcessorSupplier<String, String> {

    private final BranchingSupplier<String, String> branchingSupplier = new BranchingSupplier<String, String>(
            Arrays.asList(
                    Branch.<String, String>builder().childName("particular-processor").predicate(
                            (key, val) -> val.equals("particular")).build(),
                    Branch.<String, String>builder().childName("non-particular-processor").predicate(
                            (key, val) -> !val.equals("particular")).build())
            );

    @Override
    public org.apache.kafka.streams.processor.Processor<String, String> get() {
        return branchingSupplier.get();
    }
}
