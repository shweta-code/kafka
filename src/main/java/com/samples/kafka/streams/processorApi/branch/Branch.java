package com.samples.kafka.streams.processorApi.branch;

import org.apache.kafka.streams.kstream.Predicate;

public class Branch<K, V> {
    private final Predicate<K, V> predicate;
    private final String childName;

    public Branch(Predicate<K, V> predicate, String childName) {
        this.predicate = predicate;
        this.childName = childName;
    }

    public static <K, V> Branch.BranchBuilder<K, V> builder() {
        return new BranchBuilder<K, V>();
    }

    public Predicate<K, V> getPredicate() {
        return predicate;
    }

    public String getChildName() {
        return childName;
    }


    public static class BranchBuilder<K, V> {
        private Predicate<K, V> predicate;
        private String childName;

        public BranchBuilder<K,V> predicate(Predicate<K, V> predicate) {
            this.predicate = predicate;
            return this;
        }

        public BranchBuilder<K,V> childName(String childName) {
            this.childName = childName;
            return this;
        }

        public Branch<K,V> build() {
            return new Branch<K,V>(predicate, childName);
        }
    }
}
