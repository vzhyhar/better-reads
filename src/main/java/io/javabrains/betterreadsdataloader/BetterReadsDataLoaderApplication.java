package io.javabrains.betterreadsdataloader;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.javabrains.betterreadsdataloader.connection.DataStaxAstraProperties;
// import io.javabrains.betterreadsdataloader.utils.Parser;

@SpringBootApplication
@EnableConfigurationProperties(value = DataStaxAstraProperties.class)
public class BetterReadsDataLoaderApplication {

	// @Autowired
	// private Parser parser;

	public static void main(String[] args) {
		SpringApplication.run(BetterReadsDataLoaderApplication.class, args);
	}

	// @PostConstruct
	// public void start() {
	// parser.initAuthors();
	// parser.initWorks();
	// }

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties properties) {
		Path bundle = properties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
