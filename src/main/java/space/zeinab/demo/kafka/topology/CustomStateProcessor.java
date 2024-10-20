package space.zeinab.demo.kafka.topology;

import org.apache.kafka.streams.processor.api.ContextualProcessor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomStateProcessor extends ContextualProcessor<String, String, String, String> {
    private final String stateStoreName;
    private RelationalDbKeyValueStore stateStore;

    public CustomStateProcessor(String stateStoreName) {
        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext<String, String> context) {
        super.init(context);
        this.stateStore = context.getStateStore(stateStoreName);
    }

    @Override
    public void process(Record<String, String> record) {
        // Save to the custom state store
        stateStore.put(record.key(), record.value());

        // Forward the record to the next processor or sink
        context().forward(record);
    }

    @Override
    public void close() {
        // Cleanup logic if needed
    }
}
