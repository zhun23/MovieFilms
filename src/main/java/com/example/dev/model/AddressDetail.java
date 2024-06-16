//package com.example.dev.model;
//
//import com.example.dev.model.provinces.Provinces;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.validation.constraints.Pattern;
//
//@Embeddable
//public class AddressDetail {
//
//	@Column(nullable = false)
//	private String addressLine1;
//
//	@Column(nullable = true)
//	private String addressLine2;
//
//	@Column(nullable = false)
//    @Pattern(regexp = "\\d+", message = "El código postal debe contener solo números.")
//    private String zipcode;
//
//	@Column(nullable = false)
//	private String location;
//
//	@Enumerated(EnumType.STRING)
//	@Column(nullable = false)
//	private Provinces province;
//
//	@Enumerated(EnumType.STRING)
//	@Column(nullable = false)
//	private Countries country;
//
//	public AddressDetail() {
//		super();
//	}
//
//	public String getAddressLine1() {
//		return addressLine1;
//	}
//
//	public void setAddressLine1(String addressLine1) {
//		this.addressLine1 = addressLine1;
//	}
//
//	public String getAddressLine2() {
//		return addressLine2;
//	}
//
//	public void setAddressLine2(String addressLine2) {
//		this.addressLine2 = addressLine2;
//	}
//
//	public String getZipcode() {
//		return zipcode;
//	}
//
//	public void setZipcode(String zipcode) {
//		this.zipcode = zipcode;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public Provinces getProvince() {
//		return province;
//	}
//
//	public void setProvince(Provinces province) {
//		this.province = province;
//	}
//
//	public Countries getCountry() {
//		return country;
//	}
//
//	public void setCountrie(Countries country) {
//		this.country = country;
//	}
//}
