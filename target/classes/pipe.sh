cd ~/Downloads/kafka_2.11-2.0.0
bin/kafka-topics.sh --create --bootstrap-server 0.0.0.0:9092 --replication-factor 1 --partitions 1 --topic streams-plaintext-input
bin/kafka-topics.sh --create --bootstrap-server 0.0.0.0:9092 --replication-factor 1 --partitions 1 --topic streams-pipe-output


bin/kafka-console-producer.sh --broker-list 0.0.0.0:9092 --topic streams-plaintext-input
bin/kafka-console-consumer.sh --bootstrap-server 0.0.0.0:9092 --topic streams-pipe-output --from-beginning
