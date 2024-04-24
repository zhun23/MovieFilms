package com.example.dev.service;

import java.util.List;

import com.example.dev.dto.CreditHistoryDto;

public interface ICreditHistoryDtoService {
	public List<CreditHistoryDto> findAll();
}
