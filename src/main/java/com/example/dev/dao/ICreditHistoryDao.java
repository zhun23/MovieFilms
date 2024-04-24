package com.example.dev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev.model.CreditHistory;

@Repository
public interface ICreditHistoryDao extends JpaRepository<CreditHistory, Integer> {

}

