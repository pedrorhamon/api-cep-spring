package com.starking.correios.services;

import org.springframework.stereotype.Service;

import com.starking.correios.exception.NoContentException;
import com.starking.correios.exception.NotReadyException;
import com.starking.correios.model.Address;
import com.starking.correios.model.AddressStatus;
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
		return this.addRepository.findById(AddressStatus.DEFAULT_ID)
			.orElse(AddressStatus.builder().status(Status.NEED_SETUP).build()).getStatus();
	}

	public Address getAddressByZipcode(String zipcode) throws NoContentException, NotReadyException {
		if(this.getStatus().equals(Status.READY)) {
			throw new NotReadyException();
		}
		return this.repository.findById(zipcode)
				.orElseThrow(NoContentException::new);
	}
	
	public void setup() {

	}
	
}
