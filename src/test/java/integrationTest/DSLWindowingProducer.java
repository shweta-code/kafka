package integrationTest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class DSLWindowingProducer {

    public static void main(String[] args) throws Exception {


        String topicName = "streams-window-input";

        // create instance for properties to access producer configs
        Properties props = new Properties();

        props.put("bootstrap.servers", "0.0.0.0:9092");

        //Set acknowledgements for producer requests.
        props.put("acks", "all");

        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);

        //Specify buffer size in config
        props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer
                <>(props);


        int i = 0;
        while (i < 40) {
            producer.send(new ProducerRecord<>(topicName, "key", String.valueOf(i)));
            producer.send(new ProducerRecord<>(topicName, "key1", String.valueOf(i)));
            producer.send(new ProducerRecord<>(topicName, "key2", String.valueOf(i)));
            Thread.sleep(10000);
            i++;
        }

        System.out.println("Message sent successfully");
        producer.flush();
        producer.close();
    }
}
/*kafka -connect*/
/*Explanation of IncrementalWindows - Because commit interval is 30 sec and wondow time is 2 minutes.*/
/*
Output would look like
                key - 0 1
                key1 - 0 1
                key2 - 0 1
                key - 0 1 2 3 4
                key1 - 0 1 2 3 4
                key2 - 0 1 2 3 4
                key - 0 1 2 3 4 5 6 7
                key1 - 0 1 2 3 4 5 6 7
                key2 - 0 1 2 3 4 5 6 7
                key - 8 9 10
                key1 - 8 9 10
                key2 - 8 9 10
                key - 8 9 10 11 12 13
                key1 - 8 9 10 11 12 13
                key2 - 8 9 10 11 12 13
                key - 8 9 10 11 12 13 14 15 16
                key1 - 8 9 10 11 12 13 14 15 16
                key2 - 8 9 10 11 12 13 14 15 16
                key - 8 9 10 11 12 13 14 15 16 17 18 19
                key1 - 8 9 10 11 12 13 14 15 16 17 18 19
                key2 - 8 9 10 11 12 13 14 15 16 17 18 19
                key - 20 21 22
                key1 - 20 21 22
                key2 - 20 21 22
                key - 20 21 22 23 24 25
                key1 - 20 21 22 23 24 25
                key2 - 20 21 22 23 24 25
                key - 20 21 22 23 24 25 26 27 28
                key1 - 20 21 22 23 24 25 26 27 28
                key2 - 20 21 22 23 24 25 26 27 28
                key - 20 21 22 23 24 25 26 27 28 29 30 31
                key1 - 20 21 22 23 24 25 26 27 28 29 30 31
                key2 - 20 21 22 23 24 25 26 27 28 29 30 31
                key - 32 33 34
                key1 - 32 33 34
                key2 - 32 33 34
                key - 32 33 34 35 36 37
                key1 - 32 33 34 35 36 37
                key2 - 32 33 34 35 36 37
*/
