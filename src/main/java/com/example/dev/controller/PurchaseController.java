package com.example.dev.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dev.dto.PurchaseDto;
import com.example.dev.model.Purchase;
import com.example.dev.service.PurchaseService;

@Controller
@RequestMapping("/api")
public class PurchaseController {

	private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    
    @GetMapping("/allPurchases")
    public ResponseEntity<?> listAllPurchases(@PageableDefault(size = 24) Pageable pageable) {
	    Page<Purchase> purchases = purchaseService.findAll(pageable);

	    if (purchases.hasContent()) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("purchases", purchases.getContent());
	        response.put("currentPage", purchases.getNumber());
	        response.put("totalItems", purchases.getTotalElements());
	        response.put("totalPages", purchases.getTotalPages());

	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There's no movies in the database");
	    }
	}

    @GetMapping("/purchase/{nickname}")
    public ResponseEntity<List<Purchase>> getPurchaseByNickname(@PathVariable String nickname) {
        List<Purchase> purchases = purchaseService.findByNickname(nickname);
        return ResponseEntity.ok(purchases);
    }

    @PostMapping("/purchase/create")
    public ResponseEntity<String> createPurchase(@RequestBody PurchaseDto purchaseDto) {
        purchaseService.createPurchase(purchaseDto);
        return ResponseEntity.ok("Purchase created successfully");
    }
    
    @GetMapping("/purchase/id/{purchaseId}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Integer purchaseId) {
        Optional<Purchase> purchase = purchaseService.getPurchaseById(purchaseId);
        if (purchase.isPresent()) {
            return ResponseEntity.ok(purchase.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
