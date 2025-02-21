package space.zeinab.demo.kafka.topology;

import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import space.zeinab.demo.kafka.config.KafkaConfig;

@Component
public class CustomPipeline {
    private final JdbcTemplate jdbcTemplate;

    public CustomPipeline(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        CustomKeyValueStore keyValueStore = new CustomKeyValueStore(jdbcTemplate, KafkaConfig.TABLE_NAME);
        CustomStoreBuilder storeBuilder = new CustomStoreBuilder(keyValueStore);

        streamsBuilder.build()
                .addSource("source", KafkaConfig.INPUT_TOPIC)
                .addProcessor("CustomProcessor", () -> new CustomProcessor(keyValueStore), "source")
                .addStateStore(storeBuilder, "CustomProcessor")
                .addSink("sink", KafkaConfig.OUTPUT_TOPIC, "CustomProcessor");
    }
}
