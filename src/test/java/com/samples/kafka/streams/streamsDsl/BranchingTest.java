package com.samples.kafka.streams.streamsDsl;

import com.samples.kafka.streams.utils.JsonUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;


public class BranchingTest {
    private TestInputTopic<String, String> inputTopic;
    private TestOutputTopic<String, String> outputTopic1;
    private TestOutputTopic<String, String> outputTopic2;
    private TopologyTestDriver testDriver;
    private static final JsonUtils INSTANCE= JsonUtils.Instance();


    @Before
    public  void setup(){
        final Topology topology = Branching.getTopology();

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        testDriver = new TopologyTestDriver(topology, config);


        inputTopic = testDriver.createInputTopic(
                "streams-branch-input",
                Serdes.String().serializer(),
                Serdes.String().serializer());
        outputTopic1 = testDriver.createOutputTopic(
                "particular-topic",
                Serdes.String().deserializer(),
                Serdes.String().deserializer());
        outputTopic2 = testDriver.createOutputTopic(
                "not-particular-topic",
                Serdes.String().deserializer(),
                Serdes.String().deserializer());

    }

    public void close(){
        testDriver.close();
    }


    @Test
    public void happyPathItem(){

        inputTopic.pipeInput("1", "PARTICULAR");
        inputTopic.pipeInput("1", "NOT-PARTICULAR");
        inputTopic.pipeInput("1", "NOT-PARTICULAR");
        Assert.assertEquals( outputTopic1.readKeyValue(), new KeyValue<>("1","PARTICULAR"));
        Assert.assertEquals(2,outputTopic2.getQueueSize());
    }


}
