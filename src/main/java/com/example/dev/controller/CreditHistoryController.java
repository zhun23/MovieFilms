package com.example.dev.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.dto.CreditHistoryDto;
import com.example.dev.model.CreditHistory;
import com.example.dev.service.CreditHistoryDtoService;
import com.example.dev.service.ICreditHistoryDtoService;
import com.example.dev.service.ICreditHistoryService;

@RestController
public class CreditHistoryController {

	@Autowired
	private ICreditHistoryDtoService creditHistoryDtoService;

    @GetMapping("/history")
    public ResponseEntity<?> listCredit() {
        List<CreditHistoryDto> creditHistoryDTOs = creditHistoryDtoService.findAll();
        if (!creditHistoryDTOs.isEmpty()) {
            return ResponseEntity.ok(creditHistoryDTOs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no credit history in the database");
        }
    }
}
