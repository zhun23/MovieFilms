package com.example.dev.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.dev.dao.IAddressDao;
import com.example.dev.dao.IUserCtDao;
import com.example.dev.model.Address;

@Service
public class AddressService implements IAddressService {

	private final IUserCtDao userCtDao;

	private final IAddressDao addressDao;

	public AddressService(IAddressDao addressDao, IUserCtDao userCtDao) {
		this.addressDao = addressDao;
		this.userCtDao = userCtDao;
	}

	@Override
	public Address getAddressByUserId(Integer userId) {
		return this.addressDao.findByUser_Userid(userId).get();
	}

	@Override
	public Optional<Address> getAddressById(Integer addressid) {
        return addressDao.findById(addressid);
    }

    @Override
	public Address updateAddress(Address address) {
        return addressDao.save(address);
    }
}
