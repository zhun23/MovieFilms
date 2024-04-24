package com.example.dev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dev.dao.ICreditHistoryDao;
import com.example.dev.dto.CreditHistoryDto;
import com.example.dev.model.CreditHistory;

@Service
public class CreditHistoryService implements ICreditHistoryService {
	
	@Autowired
    private ICreditHistoryDao creditHistoryDao;

    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryDao.save(creditHistory);
    }
}

