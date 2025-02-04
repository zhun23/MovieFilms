package com.example.dev.model;

import com.example.dev.model.provinces.Provinces;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer addressid;

	@OneToOne(optional = false)
	@JoinColumn(name = "userid")
	@JsonBackReference
	private UserCt user;

	@Column(nullable = true)
	private String addressLine1;

	@Column(nullable = true)
	private String addressLine2;

	@Column(nullable = true)
    @Pattern(regexp = "\\d+", message = "El código postal debe contener solo números.")
    private String zipcode;

	@Column(nullable = true)
	private String location;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private Provinces province;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private Countries country;

	public Address() {
		super();
	}

	public Integer getAddressid() {
		return addressid;
	}

	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}

	public UserCt getUser() {
		return user;
	}

	public void setUser(UserCt user) {
		this.user = user;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Provinces getProvince() {
		return province;
	}

	public void setProvince(Provinces province) {
		this.province = province;
	}

	public Countries getCountry() {
		return country;
	}

	public void setCountry(Countries country) {
		this.country = country;
	}

}
