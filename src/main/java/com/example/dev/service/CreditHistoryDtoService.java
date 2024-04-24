package com.example.dev.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dev.dao.ICreditHistoryDao;
import com.example.dev.dto.CreditHistoryDto;
import com.example.dev.model.CreditHistory;

@Service
public class CreditHistoryDtoService implements ICreditHistoryDtoService {
	
	@Autowired
    private ICreditHistoryDao creditHistoryDao;
	
	public List<CreditHistoryDto> findAll() {
        
        List<CreditHistory> creditHistories = creditHistoryDao.findAll();
        return creditHistories.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
	
	private CreditHistoryDto convertToDto(CreditHistory creditHistory) {
        return new CreditHistoryDto(creditHistory);
    }
}
