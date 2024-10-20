package space.zeinab.demo.kafka.topology;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.processor.StateStoreContext;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RelationalDbKeyValueStore implements KeyValueStore<String, String> {
    private final String storeName;
    private final JdbcTemplate jdbcTemplate;
    private StateStoreContext context;

    public RelationalDbKeyValueStore(String storeName, JdbcTemplate jdbcTemplate) {
        this.storeName = storeName;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void init(StateStoreContext context) {
        this.context = context;
    }

    @Override
    public void put(String key, String value) {
        String sql = "INSERT INTO stream_state (key, value) VALUES (?, ?) ON DUPLICATE KEY UPDATE value = ?";
        jdbcTemplate.update(sql, key, value, value);
    }

    @Override
    public String putIfAbsent(String s, String s2) {
        return "";
    }

    @Override
    public void putAll(List<KeyValue<String, String>> list) {

    }

    @Override
    public String delete(String s) {
        return "";
    }

    @Override
    public String get(String key) {
        String sql = "SELECT value FROM stream_state WHERE key = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{key}, String.class);
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
    public void close() {
        // Cleanup logic if needed
    }

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
        return storeName;
    }

    @Override
    public void init(ProcessorContext processorContext, StateStore stateStore) {

    }

    @Override
    public void flush() {

    }

    // Other KeyValueStore methods can be implemented similarly, interacting with the relational DB
}

