package com.example.dev.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.dev.dto.CreditHistoryDto;
import com.example.dev.model.CreditHistory;

public interface ICreditHistoryDtoService {
	
	//public List<CreditHistoryDto> findAll();
	
	public Page<CreditHistoryDto> findAllReversed(Pageable pageable);
	
	//public Page<CreditHistoryDto> findByUserUseridOrderByHistoryidDesc(int userid, Pageable pageable);

	public Page<CreditHistoryDto> findByUserCtUseridOrderByHistoryidDesc(int userId, Pageable pageable);
}
