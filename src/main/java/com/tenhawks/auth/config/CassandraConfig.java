package com.tenhawks.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.cassandra.core.keyspace.KeyspaceOption;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;

import java.util.Collections;
import java.util.List;


/**
 * @author Mukhtiar
 */
@Configuration
public class CassandraConfig  extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    protected String keyspaceName;

    @Override
    protected String getKeyspaceName() {
        return this.keyspaceName;
    }

    @Override
    protected List getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification
                .createKeyspace(keyspaceName).ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication());
    }

    @Override
    protected List getStartupScripts() {
        return Collections.singletonList("CREATE KEYSPACE IF NOT EXISTS "
                + keyspaceName + " WITH replication = {"
                + " 'class': 'SimpleStrategy', "
                + " 'replication_factor': '1' " + "};");

    }
}