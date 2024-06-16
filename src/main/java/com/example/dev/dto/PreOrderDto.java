package com.example.dev.dto;

import java.util.List;

import com.example.dev.model.Countries;
import com.example.dev.model.provinces.Provinces;

public class PreOrderDto {
    private List<CartDetailDto> cartDetailsDto;
    private String addressLine1;
    private String addressLine2;
    private String zipcode;
    private String location;
    private Provinces province;
    private Countries country;
    private double orderPrice;
    private double iva;
    private double igic;
    private Integer shippingCost;
    private double totalOrderAmount;

    public PreOrderDto(List<CartDetailDto> cartDetailsDto, String addressLine1, String addressLine2, String zipcode, String location, Provinces province, Countries country) {
        this.cartDetailsDto = cartDetailsDto;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.zipcode = zipcode;
        this.location = location;
        this.province = province;
        this.country = country;
        this.orderPrice = calculateOrderPrice();
        this.iva = this.orderPrice * 0.21;
        this.igic = this.orderPrice * 0.07;
        this.shippingCost = calculateShippingCost();
        this.totalOrderAmount = this.orderPrice + this.getTax() + this.shippingCost;
    }
    
    private double calculateOrderPrice() {
        double totalPriceWithTax = cartDetailsDto.stream()
                .mapToDouble(cartDetail -> cartDetail.getQuantity() * cartDetail.getCatalogue().getPrice())
                .sum();

        double totalPriceWithoutTax = totalPriceWithTax / 1.21;
        return totalPriceWithoutTax;
    }

    private Integer calculateShippingCost() {
        switch(province) {
            case Baleares:
            case Ceuta:
            case Las_Palmas:
            case Melilla:
            case Santa_Cruz_De_Tenerife:
                if (orderPrice > 57.84) {
                    return 0;
                } else {
                    return 15;
                }
            default:
                if (orderPrice > 41.31) {
                    return 0;
                } else {
                    return 5;
                }
        }
    }

    public List<CartDetailDto> getCartDetails() {
        return cartDetailsDto;
    }

    public void setCartDetails(List<CartDetailDto> cartDetails) {
        this.cartDetailsDto = cartDetails;
        this.orderPrice = calculateOrderPrice();
        this.iva = this.orderPrice * 0.21;
        this.igic = this.orderPrice * 0.07;
        this.shippingCost = calculateShippingCost();
        this.totalOrderAmount = this.orderPrice + this.getTax() + this.shippingCost;
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

    public Double getOrderPrice() {
        return orderPrice;
    }

    public Double getIva() {
        return iva;
    }

    public Double getIgic() {
        return igic;
    }

    public Integer getShippingCost() {
        return shippingCost;
    }

    public Double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public double getTax() {
        switch(province) {
            case Baleares:
            case Ceuta:
            case Las_Palmas:
            case Melilla:
            case Santa_Cruz_De_Tenerife:
                return igic;
            default:
                return iva;
        }
    }
}
