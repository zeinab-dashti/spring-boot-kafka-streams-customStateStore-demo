package space.zeinab.demo.kafka.topology;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class CustomProcessor implements Processor<String, String, String, String> {
    private ProcessorContext<String, String> context;
    private final CustomKeyValueStore stateStore;

    public CustomProcessor(CustomKeyValueStore stateStore) {
        this.stateStore = stateStore;
    }

    @Override
    public void init(ProcessorContext<String, String> context) {
        this.context = context;
    }

    @Override
    public void process(Record<String, String> record) {
        stateStore.put(record.key(), record.value());

        log.info("data from DB: {}", stateStore.get(record.key()));

        context.forward(record);
    }

    @Override
    public void close() {}
}