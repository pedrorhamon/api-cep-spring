package com.starking.correios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	private String zipCode;
	private String street;
	private String district;
	private String city;
	private String state;
}
