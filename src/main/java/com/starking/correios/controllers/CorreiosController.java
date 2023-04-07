package com.starking.correios.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starking.correios.model.Address;

import jakarta.websocket.server.PathParam;

@RestController
public class CorreiosController {

	@GetMapping("/status")
	public String getStatus() {
		return "up";
	}

	@GetMapping("/zipcode/{zipcode}")
	public Address getAddressByZipCode(@PathParam("zipcode") String zipcode) {
		return Address.builder().zipCode(zipcode).build();
	}

}
