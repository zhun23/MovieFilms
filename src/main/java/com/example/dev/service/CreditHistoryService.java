package com.example.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dev.dao.ICreditHistoryDao;
import com.example.dev.model.CreditHistory;

@Service
public class CreditHistoryService {
	
	@Autowired
    private ICreditHistoryDao creditHistoryDao;

    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryDao.save(creditHistory);
    }
}
