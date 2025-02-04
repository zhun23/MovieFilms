package com.example.dev.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dev.dao.IPurchaseItemDao;
import com.example.dev.dto.TitleCountDto;
import com.example.dev.model.PurchaseItem;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PurchaseItemService implements IPurchaseItemService{

	 private final IPurchaseItemDao purchaseItemDao;

	 public PurchaseItemService(IPurchaseItemDao purchaseItemDao) {
		 this.purchaseItemDao = purchaseItemDao;
	 }

	 public List<PurchaseItem> findByPurchaseId(Integer purchaseId) {
		 return purchaseItemDao.findByPurchaseId(purchaseId);
	 }

	 public PurchaseItem save(PurchaseItem purchaseItem) {
	     return purchaseItemDao.save(purchaseItem);
	 }

	 public void deleteById(Integer id) {
	   	purchaseItemDao.deleteById(id);
     }
	 
	 public List<TitleCountDto> getTitleCounts() {
	        return purchaseItemDao.countByTitle();
	    }
}
