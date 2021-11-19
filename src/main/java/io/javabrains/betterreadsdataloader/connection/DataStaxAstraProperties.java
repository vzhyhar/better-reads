package io.javabrains.betterreadsdataloader.connection;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "datastax.astra")
public class DataStaxAstraProperties {
    
    @Getter
    @Setter
    private File secureConnectBundle;

}
