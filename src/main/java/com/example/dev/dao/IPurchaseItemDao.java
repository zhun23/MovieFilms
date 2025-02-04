package com.example.dev.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.dev.dto.TitleCountDto;
import com.example.dev.model.PurchaseItem;

@Repository
public interface IPurchaseItemDao extends JpaRepository<PurchaseItem, Integer>{
	
	List<PurchaseItem> findByPurchaseId(Integer purchaseId);
	
	@Query("SELECT new com.example.dev.dto.TitleCountDto(p.title, COUNT(p)) FROM PurchaseItem p GROUP BY p.title")
    List<TitleCountDto> countByTitle();
}
