package com.starking.correios.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.starking.correios.model.Address;

@Repository
public class SetupRepository {
	
	public List<Address> getFromOrigin() {
		return new ArrayList<>();
	}
}
