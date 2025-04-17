package org.fawry.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "org.fawry.store")
@EnableDiscoveryClient
public class StoreApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(StoreApiApplication.class, args);
	}
}
