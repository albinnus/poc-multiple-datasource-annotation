package com.example.poc.infra.config;

import com.example.poc.infra.interceptors.ReadOnlyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RoutingDsConfig extends AbstractRoutingDataSource {

     RoutingDsConfig(DataSource writer, DataSource reader) {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("writer", writer);
        dataSources.put("reader", reader);
        setTargetDataSources(dataSources);
        setDefaultTargetDataSource(writer);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceMode = ReadOnlyContext.isReadOnly() ? "reader" : "writer";
        return dataSourceMode;
    }
}
