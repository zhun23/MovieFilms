package com.example.dev.dao;

import java.util.Optional;

import com.example.dev.model.Address;

public interface IAddressDao {
	
	Address save(Address address);
	
    Optional<Address> findByUser_Userid(Integer userid);
    
    Optional<Address> findById(Integer addressid);
    
}
