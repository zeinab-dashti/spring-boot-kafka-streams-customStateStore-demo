package space.zeinab.demo.kafka.topology;

import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;

import java.util.Map;

public class CustomStoreBuilder implements StoreBuilder<KeyValueStore<String, String>> {
    private final CustomKeyValueStore store;

    public CustomStoreBuilder(CustomKeyValueStore store) {
        this.store = store;
    }

    @Override
    public StoreBuilder<KeyValueStore<String, String>> withCachingEnabled() {
        return this;
    }

    @Override
    public StoreBuilder<KeyValueStore<String, String>> withCachingDisabled() {
        return this;
    }

    @Override
    public StoreBuilder<KeyValueStore<String, String>> withLoggingEnabled(Map<String, String> map) {
        return this;
    }

    @Override
    public StoreBuilder<KeyValueStore<String, String>> withLoggingDisabled() {
        return this;
    }

    @Override
    public KeyValueStore<String, String> build() {
        return store;
    }

    @Override
    public Map<String, String> logConfig() {
        return Map.of();
    }

    @Override
    public boolean loggingEnabled() {
        return false;
    }

    @Override
    public String name() {
        return store.name();
    }
}