package com.example.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_item")
public class PurchaseItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseItemid;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    @JsonIgnore
    private Purchase purchase;
    
    @Column
    private String title;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    public PurchaseItem() {
    }

    public PurchaseItem(String title, Integer quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    public PurchaseItem(Purchase purchase, String title, Integer quantity) {
        this.purchase = purchase;
        this.title = title;
        this.quantity = quantity;
    }
    
    public Integer getPurchaseItemid() {
        return purchaseItemid;
    }

    public void setPurchaseItemid(Integer purchaseItemid) {
        this.purchaseItemid = purchaseItemid;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
	@Override
    public String toString() {
        return "PurchaseItem{" +
                "purchaseItemId=" + purchaseItemid +
                ", title='" + title + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
