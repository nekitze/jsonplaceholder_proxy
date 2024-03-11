package edu.nikitazubov.jsonplaceholderproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JsonPlaceholderProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonPlaceholderProxyApplication.class, args);
	}

}
