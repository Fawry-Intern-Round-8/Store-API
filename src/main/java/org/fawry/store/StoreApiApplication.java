package org.fawry.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.fawry.store")
public class StoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApiApplication.class, args);
	}

}
