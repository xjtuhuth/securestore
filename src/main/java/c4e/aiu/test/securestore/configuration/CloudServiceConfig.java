package c4e.aiu.test.securestore.configuration;

import javax.sql.DataSource;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudServiceConfig extends AbstractCloudConfig {

	@Bean
	public DataSource secureStoreDataSource() {
		
		// use the name of the service instance to create the data source
		return connectionFactory().dataSource("sample-securestore");
	}

	@Bean
	public DataSource schemaDataSource() {

		// use the name of the service instance to create the data source
		return connectionFactory().dataSource("sample-schema");
	}

}
