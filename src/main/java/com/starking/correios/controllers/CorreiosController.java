package com.starking.correios.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starking.correios.model.Address;

@RestController
@RequestMapping
public class CorreiosController {

	public String getStatus() {
		return "up";
	}

	public Address getAddressByZipCode(String zipCode) {
		return null;
	}

}
