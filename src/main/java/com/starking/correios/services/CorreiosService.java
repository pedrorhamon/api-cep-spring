package com.starking.correios.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.starking.correios.CorreiosApplication;
import com.starking.correios.exception.NoContentException;
import com.starking.correios.exception.NotReadyException;
import com.starking.correios.model.Address;
import com.starking.correios.model.AddressStatus;
import com.starking.correios.model.Status;
import com.starking.correios.repositories.AddressRepository;
import com.starking.correios.repositories.AddressStatusRepository;
import com.starking.correios.repositories.SetupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorreiosService {

	private final AddressRepository addressRepository;
	
	private final AddressStatusRepository addressStatusRepository;
	
	private final SetupRepository setupRepository;

	public Status getStatus() {
		return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)
			.orElse(AddressStatus.builder().status(Status.NEED_SETUP).build()).getStatus();
	}

	public Address getAddressByZipcode(String zipcode) throws NoContentException, NotReadyException {
		if(this.getStatus().equals(Status.READY)) {
			throw new NotReadyException();
		}
		return this.addressRepository.findById(zipcode)
				.orElseThrow(NoContentException::new);
	}
	
	private void saveStatus(Status status) {
		this.addressStatusRepository.save(AddressStatus.builder()
				.id(AddressStatus.DEFAULT_ID)
				.status(status).build());
	}
	
	protected void setupOnStartup() {
		try {
			this.setup();
		}catch(Exception e) {
			CorreiosApplication.close(999);
		}
	}
	
	public void setup() throws IOException {
		if(this.getStatus().equals(Status.READY)) {
			this.saveStatus(Status.SETUP_RUNNING);
			
			try {
				this.addressRepository.saveAll(this.setupRepository.getFromOrigin());
			}catch(Exception e) {
				this.saveStatus(Status.NEED_SETUP);
				throw e;
			}
			this.saveStatus(Status.READY);
		}
	}
	
}
