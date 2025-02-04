package com.example.dev.service;

import java.util.List;

import com.example.dev.dto.TitleCountDto;
import com.example.dev.model.PurchaseItem;

public interface IPurchaseItemService {
	
	List<PurchaseItem> findByPurchaseId(Integer purchaseId);
	
	public PurchaseItem save(PurchaseItem purchaseItem);
	
	public void deleteById(Integer id);
	
	public List<TitleCountDto> getTitleCounts();
}
