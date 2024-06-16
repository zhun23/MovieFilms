package com.example.dev.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.dto.AddressDto;
import com.example.dev.model.Address;
import com.example.dev.model.UserCt;
import com.example.dev.service.IAddressService;
import com.example.dev.service.IUserCtService;

@RestController
@RequestMapping("/api")
public class AddressController {

	private final IAddressService addressService;

	private final IUserCtService userCtService;

	public AddressController(IAddressService addressService, IUserCtService userCtService) {
		this.addressService = addressService;
		this.userCtService = userCtService;
	}

	@GetMapping("/getAddress/{addresId}")
    public ResponseEntity<Address> getAddressesByUserId(@PathVariable Integer addresId) {
        Address address = addressService.getAddressByUserId(addresId);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

	@GetMapping("/getAddressByUser/{userid}")
	public ResponseEntity<?> findByUserid(@PathVariable Integer userid) {
	    Optional<UserCt> optionalUser = userCtService.findByUserid(userid);

	    if (optionalUser.isPresent()) {
	        UserCt user = optionalUser.get();
	        AddressDto addressDto = new AddressDto();

	        addressDto.setAddressLine1(user.getAddress().getAddressLine1());
	        addressDto.setAddressLine2(user.getAddress().getAddressLine2());
	        addressDto.setZipcode(user.getAddress().getZipcode());
	        addressDto.setLocation(user.getAddress().getLocation());
	        addressDto.setProvince(user.getAddress().getProvince());
	        addressDto.setCountry(user.getAddress().getCountry());

	        return ResponseEntity.ok(addressDto);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No user matching with that ID was found in the database");
	    }
	}

	@GetMapping("/address/{id}")
	public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
	    Optional<Address> addressOptional = addressService.getAddressById(id);

	    if (addressOptional.isPresent()) {
	        Address address = addressOptional.get();
	        return ResponseEntity.ok(address);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

    @PutMapping("address/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address addressDetails) {
        Optional<Address> addressOptional = addressService.getAddressById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            address.setAddressLine1(addressDetails.getAddressLine1());
            address.setAddressLine2(addressDetails.getAddressLine2());
            address.setZipcode(addressDetails.getZipcode());
            address.setLocation(addressDetails.getLocation());
            address.setProvince(addressDetails.getProvince());
            address.setCountry(addressDetails.getCountry());
            Address updatedAddress = addressService.updateAddress(address);
            return ResponseEntity.ok(updatedAddress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
