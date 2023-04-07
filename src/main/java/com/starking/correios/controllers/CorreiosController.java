package com.starking.correios.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starking.correios.exception.NoContentException;
import com.starking.correios.model.Address;
import com.starking.correios.services.CorreiosService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CorreiosController {
	
	private final CorreiosService service;

	@GetMapping("/status")
	public String getStatus() {
		return "Service status: " + this.service.getStatus();
	}

	@GetMapping("/zipcode/{zipcode}")
	public Address getAddressByZipCode(@PathParam("zipcode") String zipcode) throws NoContentException {
		return this.service.getAddressByZipcode(zipcode);
	}

}
