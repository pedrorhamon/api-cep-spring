package com.starking.correios.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
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

@Service
public class CorreiosService {
	private static Logger logger = LoggerFactory.getLogger(CorreiosService.class);
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressStatusRepository statusRepository;
	
	@Autowired
	private SetupRepository setupRepository;
	
	@Value("${setup.on.startup}")
	private boolean setupOnStartup;
	
	@Value("${spring.datasource.url}")
	private String a;
	
	
	public Status getStatus() {
		return statusRepository.findById(AddressStatus.DEFAULT_ID)
				.orElse(AddressStatus.builder().status(Status.NEED_SETUP).build()).getStatus();
	}
	
	public Address getByZipcode(String zipcode) throws NotReadyException {
		if (!this.getStatus().equals(Status.READY))
			throw new NotReadyException();
		
		return addressRepository.findById(zipcode).orElseThrow(NoContentException::new);
	}
	
	private void saveServiceStatus(Status status) {
		statusRepository.save(AddressStatus.builder().id(AddressStatus.DEFAULT_ID).status(status).build());
	}

    @Async
    @EventListener(ApplicationStartedEvent.class)
	protected void setupOnStartup() {
		if (setupOnStartup) {
			this.setup();
		}
	}
	
	public synchronized void setup() {
		logger.info("---" + a);
		logger.info("---");
		logger.info("--- STARTING SETUP");
		logger.info("--- Please wait... This may take a few minutes");
		logger.info("---");
		logger.info("---");
		
		try {
			if (this.getStatus().equals(Status.NEED_SETUP)) { // If not running, starts it.
				this.saveServiceStatus(Status.SETUP_RUNNING);
				
				//
				// Download CSV content
				// From origin and saves it.
				this.addressRepository.saveAll(
						setupRepository.listAdressesFromOrigin());
				
				//
				// Set service READY!
				this.saveServiceStatus(Status.READY);
			}
			
			logger.info("---");
			logger.info("---");
			logger.info("--- READY TO USE");
			logger.info("--- Good luck my friend :)");
			logger.info("---");
			logger.info("---");
		} catch(Exception exc) {
			logger.error("Error to download/save addresses, closing the application....", exc);
			CorreiosApplication.close(999);
		}
	}
}
