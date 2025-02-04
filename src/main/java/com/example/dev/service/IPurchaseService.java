package com.example.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.dev.model.Purchase;

public interface IPurchaseService {
	
	public Page<Purchase> findAll(Pageable pageable);

	public List<Purchase> findByNickname(String nickname);
	
	public void save(Purchase purchase);
	
	public void deleteById(Integer id);
	
	public Optional<Purchase> getPurchaseById(Integer purchaseId);
	
}
