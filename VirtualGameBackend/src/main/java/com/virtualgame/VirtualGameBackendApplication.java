package com.virtualgame;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class VirtualGameBackendApplication {

	public static void main(String[] args) {
        log.info("Init virtualGame");
		SpringApplication.run(VirtualGameBackendApplication.class, args);
	}
}
