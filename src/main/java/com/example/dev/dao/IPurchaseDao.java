package com.example.dev.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dev.model.Purchase;

@Repository
public interface IPurchaseDao extends JpaRepository<Purchase, Integer> {
	
	@Query("SELECT p FROM Purchase p WHERE p.nickname = :nickname")
    List<Purchase> findByNickname(@Param("nickname") String nickname);
	
}
