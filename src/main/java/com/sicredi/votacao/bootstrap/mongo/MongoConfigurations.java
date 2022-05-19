package com.sicredi.votacao.bootstrap.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfigurations extends AbstractMongoClientConfiguration {
    private List<Converter<?, ?>> converters = new ArrayList<>();

    @Value("${mongo.database}")
    private String database;
    @Value("${mongo.host}")
    private String host;
    @Value("${mongo.port}")
    private String port;
    @Value("${mongo.username}")
    private String username;
    @Value("${mongo.password}")
    private String password;

    @Override
    public MongoClient mongoClient() {
        final ConnectionString connectionString = new ConnectionString(String.format("mongodb+srv://%s:%s@%s/%s", username, password, host, database));
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoCustomConversions customConversions() {
        converters.add(new OffSetDateTimeReadConverter());
        converters.add(new OffSetDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }
}
