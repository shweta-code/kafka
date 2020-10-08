cd ~/Downloads/kafka_2.11-2.0.0
bin/kafka-topics.sh --create \
    --zookeeper 0.0.0.0:2181 \
    --replication-factor 1 \
    --partitions 1 \
    --topic process-branch-input
bin/kafka-topics.sh --create \
    --zookeeper 0.0.0.0:2181 \
    --replication-factor 1 \
    --partitions 1 \
    --topic particular-topic
bin/kafka-topics.sh --create \
    --zookeeper 0.0.0.0:2181 \
    --replication-factor 1 \
    --partitions 1 \
    --topic not-particular-topic

bin/kafka-console-consumer.sh --bootstrap-server 0.0.0.0:9092 \
    --topic process-branch-input \
    --from-beginning \
    --property print.key=true \
    --property print.value=true \
    --property key.separator=" - "