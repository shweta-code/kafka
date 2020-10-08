package com.samples.kafka.streams.processorApi.branch;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.To;

import java.util.List;

public class BranchingSupplier<K, V> implements ProcessorSupplier<K, V> {

    private final List<Branch<K, V>> branches;

    @SuppressWarnings("unchecked")
    public BranchingSupplier(List<Branch<K, V>> branches) {
        this.branches = branches;
    }


    @Override
    public Processor<K, V> get() {
        return new KStreamBranchProcessor();
    }

    private class KStreamBranchProcessor extends AbstractProcessor<K, V> {
        @Override
        public void process(K key, V value) {
            for (Branch<K, V> branch : branches) {
                if (branch.getPredicate().test(key, value)) {
                    // use forward with childIndex here and then break the loop
                    // so that no record is going to be piped to multiple streams
                    context().forward(key, value, To.child(branch.getChildName()));
                    break;
                }
            }
        }
    }


}

