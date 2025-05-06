package com.example.flightTracker;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@Log4j2
public class FlightTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightTrackerApplication.class, args);
	}

}
