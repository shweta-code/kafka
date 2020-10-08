package integrationTest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class DSLBranchingProducer {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) throws Exception {


        String topicName = "streams-branch-input";

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
            producer.send(new ProducerRecord<>(topicName, String.valueOf(i), "PARTICULAR"));
            producer.send(new ProducerRecord<>(topicName, String.valueOf(i), "NOT-PARTICULAR"));
            Thread.sleep(10000);
            i++;
        }

        System.out.println("Message sent successfully");
        producer.flush();
        producer.close();
    }
}

//https://stackoverflow.com/questions/49482873/how-to-deploy-kafka-stream-applications-on-kubernetes