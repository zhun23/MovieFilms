package com.example.dev.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev.dto.CreditHistoryDto;
import com.example.dev.service.ICreditHistoryDtoService;

@RestController
public class CreditHistoryController {

	@Autowired
	private ICreditHistoryDtoService creditHistoryDtoService;

	@GetMapping("/history")
    public ResponseEntity<?> listCredit(@PageableDefault(size = 24) Pageable pageable) {
        Page<CreditHistoryDto> creditHistoryDtos = creditHistoryDtoService.findAllReversed(pageable);

        if (creditHistoryDtos.hasContent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("creditHistories", creditHistoryDtos.getContent());
            response.put("currentPage", creditHistoryDtos.getNumber());
            response.put("totalItems", creditHistoryDtos.getTotalElements());
            response.put("totalPages", creditHistoryDtos.getTotalPages());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no credit history in the database");
        }
    }

	@GetMapping("/creditHistory/id/{userId}")
	public ResponseEntity<?> findCreditHistoryByUserCtId(@PathVariable int userId, @PageableDefault(size = 24) Pageable pageable) {
	    Page<CreditHistoryDto> creditHistoryDtos = creditHistoryDtoService.findByUserCtUseridOrderByHistoryidDesc(userId, pageable);
	    if (creditHistoryDtos.hasContent()) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("creditHistories", creditHistoryDtos.getContent());
	        response.put("currentPage", creditHistoryDtos.getNumber());
	        response.put("totalItems", creditHistoryDtos.getTotalElements());
	        response.put("totalPages", creditHistoryDtos.getTotalPages());

	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no credit history found with this ID user in the database");
	    }
	}
}
