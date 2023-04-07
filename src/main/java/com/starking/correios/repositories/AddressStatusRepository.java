package com.starking.correios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.starking.correios.model.AddressStatus;

public interface AddressStatusRepository extends JpaRepository<AddressStatus, Long>{

}
