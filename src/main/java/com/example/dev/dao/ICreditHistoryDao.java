package com.example.dev.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev.model.CreditHistory;

@Repository
public interface ICreditHistoryDao extends JpaRepository<CreditHistory, Integer> {

	Page<CreditHistory> findAll(Pageable pageable);

	//Page<CreditHistory> findByUserId(int userId, Pageable pageable);
	
	Page<CreditHistory> findByUserCtUseridOrderByHistoryidDesc(int userid, Pageable pageable);
	
	List<CreditHistory> findAllByOrderByHistoryidDesc(Pageable pageable);
}

