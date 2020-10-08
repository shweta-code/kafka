package com.samples.kafka.streams.processorApi.window.processor;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.internals.Change;
import org.apache.kafka.streams.kstream.internals.KStreamFlatTransform;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class TransformingProcessorSuppler implements ProcessorSupplier<Windowed<String>, Change<String>> {
    private final ProcessorSupplier<Windowed<String>, Change<String>> processorSupplier =
            new KStreamFlatTransform<>(() -> new Transformer<Windowed<String>, Change<String>, Iterable<KeyValue<String, String>>>() {

                @Override
                public void init(ProcessorContext context) {
                }

                @Override
                public Iterable<KeyValue<String, String>> transform(Windowed<String> windowed, Change<String> value) {
                    //print window time
                    printWindowTime(windowed, value);

                    //No context forwarding in transformers otherwise duplicate messages are sent to producer
                    //this.context.forward(windowed.key(), value.newValue);

                    KeyValue<String, String> keyValuePair = new KeyValue<>(windowed.key(), value.newValue);
                    return Collections.singleton(keyValuePair);
                }

                private void printWindowTime(Windowed<String> windowed, Change<String> value) {
                    LocalDateTime windowStart = Instant.ofEpochMilli(windowed.window().start())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    LocalDateTime windowEnd = Instant.ofEpochMilli(windowed.window().end())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    System.out.println("Window time " + windowStart.format(dtf) + " " + windowEnd.format(dtf));


                    System.out.println("Windowed key value " + windowed + " " + value.newValue);
                }


                @Override
                public void close() {

                }
            });

    @Override
    public Processor<Windowed<String>, Change<String>> get() {
        return processorSupplier.get();
    }

}
