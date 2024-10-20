package space.zeinab.demo.kafka.topology;

import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.Map;

public class RelationalDbStoreBuilder implements StoreBuilder<StateStore> {
    private final String name;
    private final JdbcTemplate jdbcTemplate;

    public RelationalDbStoreBuilder(String name, JdbcTemplate jdbcTemplate) {
        this.name = name;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StoreBuilder<StateStore> withCachingEnabled() {
        return null;
    }

    @Override
    public StoreBuilder<StateStore> withCachingDisabled() {
        return null;
    }

    @Override
    public StoreBuilder<StateStore> withLoggingEnabled(Map<String, String> map) {
        return null;
    }

    @Override
    public StoreBuilder<StateStore> withLoggingDisabled() {
        return null;
    }

    @Override
    public StateStore build() {
        return new RelationalDbKeyValueStore(name, jdbcTemplate);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean loggingEnabled() {
        return false;
    }

    @Override
    public Map<String, String> logConfig() {
        return Collections.emptyMap();
    }

    @Override
    public boolean isWindowStore() {
        return false;
    }
}