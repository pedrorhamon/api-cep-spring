package com.starking.correios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starking.correios.model.Address;

public interface AddressRepository extends JpaRepository<Address, String>{

}
