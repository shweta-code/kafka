cd ~/Downloads/kafka_2.11-2.0.0
bin/kafka-topics.sh --create \
    --zookeeper 0.0.0.0:2181 \
    --replication-factor 1 \
    --partitions 1 \
    --topic streams-plaintext-input
bin/kafka-topics.sh --create \
    --zookeeper 0.0.0.0:2181 \
    --replication-factor 1 \
    --partitions 1 \
    --topic streams-wordcount-output \
    --config cleanup.policy=compact


bin/kafka-console-producer.sh --broker-list 0.0.0.0:9092 --topic streams-plaintext-input

bin/kafka-console-consumer.sh --bootstrap-server 0.0.0.0:9092 \
    --topic streams-wordcount-output \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer