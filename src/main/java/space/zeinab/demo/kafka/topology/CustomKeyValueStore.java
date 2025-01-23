package space.zeinab.demo.kafka.topology;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

public class CustomKeyValueStore implements KeyValueStore<String, String> {
    private final JdbcTemplate jdbcTemplate;
    private final String tableName;

    public CustomKeyValueStore(JdbcTemplate jdbcTemplate, String tableName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    @Override
    public void put(String key, String value) {
        jdbcTemplate.update(
                "MERGE INTO "  + tableName +
                        " USING dual ON (id = ?) " +
                        "WHEN MATCHED THEN UPDATE SET name = ? " +
                        "WHEN NOT MATCHED THEN INSERT (id, name) VALUES (?, ?)",
                key, value, key, value
        );
    }

    @Override
    public String putIfAbsent(String s, String s2) {
        return "";
    }

    @Override
    public void putAll(List<KeyValue<String, String>> list) {}

    @Override
    public String delete(String s) {
        return "";
    }

    @Override
    public String get(String key) {
        return jdbcTemplate.queryForList(
                "SELECT name FROM "  + tableName +
                        " WHERE id = ?", String.class, key
        ).get(0).toUpperCase();
    }

    @Override
    public KeyValueIterator<String, String> range(String s, String k1) {
        return null;
    }

    @Override
    public KeyValueIterator<String, String> all() {
        return null;
    }

    @Override
    public long approximateNumEntries() {
        return 0;
    }

    @Override
    public void close() {}

    @Override
    public boolean persistent() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public String name() {
        return tableName;
    }

    @Override
    public void init(ProcessorContext processorContext, StateStore stateStore) {}

    @Override
    public void flush() {}
}