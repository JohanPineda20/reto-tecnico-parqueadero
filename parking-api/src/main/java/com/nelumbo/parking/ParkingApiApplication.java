package com.nelumbo.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Johan Pineda
 */
@EnableFeignClients
@SpringBootApplication
public class ParkingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingApiApplication.class, args);
	}

}
