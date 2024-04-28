package com.example.dev.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	public Page<CreditHistoryDto> findAllReversed(Pageable pageable) {
        Pageable reversedPageable = PageRequest.of(
            pageable.getPageNumber(), 
            pageable.getPageSize(), 
            Sort.by("id").descending()
        );
        Page<CreditHistory> creditHistories = creditHistoryDao.findAll(reversedPageable);
        return creditHistories.map(this::convertToDto);
    }
	
	private CreditHistoryDto convertToDto(CreditHistory creditHistory) {
        return new CreditHistoryDto(creditHistory);
    }
	
	public Page<CreditHistoryDto> findByUserIdOrderByIdDesc(int userId, Pageable pageable) {
        return creditHistoryDao.findByUserIdOrderByIdDesc(userId, pageable)
                                     .map(this::convertToDto);
    }
}