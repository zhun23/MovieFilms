package com.example.dev.dto;

import java.util.List;

public class PurchaseDto {

	private String nickname;
    private String addressLine1;
    private String addressLine2;
    private String zipcode;
    private String location;
    private String province;
    private String country;
    private double shippingCost;
    private double totalOrderAmount;
    private List<CartDetailDto> cartDetails;
    
	public PurchaseDto() {
		super();
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}
	public double getTotalOrderAmount() {
		return totalOrderAmount;
	}
	public void setTotalOrderAmount(double totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}
	public List<CartDetailDto> getCartDetails() {
		return cartDetails;
	}
	public void setCartDetails(List<CartDetailDto> cartDetails) {
		this.cartDetails = cartDetails;
	}
}
