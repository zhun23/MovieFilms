package com.example.dev.service;

import java.util.Optional;

import com.example.dev.model.Address;

public interface IAddressService {

	public Address getAddressByUserId(Integer userId);

	public Address updateAddress(Address address);

	public Optional<Address> getAddressById(Integer id);

}
