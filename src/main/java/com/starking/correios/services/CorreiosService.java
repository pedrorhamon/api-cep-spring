package com.starking.correios.services;

import org.springframework.stereotype.Service;

import com.starking.correios.exception.NoContentException;
import com.starking.correios.model.Address;
import com.starking.correios.model.Status;
import com.starking.correios.repositories.AddressRepository;
import com.starking.correios.repositories.AddressStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorreiosService {

	private final AddressRepository repository;
	
	private final AddressStatusRepository addRepository;

	public Status getStatus() {
		return Status.READY;
	}

	public Address getAddressByZipcode(String zipcode) throws NoContentException {
		return this.repository.findById(zipcode)
				.orElseThrow(NoContentException::new);
	}
	
	public void setup() {

	}
	
}
