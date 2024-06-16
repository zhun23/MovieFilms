package com.example.dev.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dev.model.Address;

public interface IAddressDao extends JpaRepository<Address, Integer>{

	Optional<Address> findByUser_Userid(Integer userid);

}
