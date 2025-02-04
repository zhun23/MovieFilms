package com.example.dev.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dev.dto.TitleCountDto;
import com.example.dev.model.PurchaseItem;
import com.example.dev.service.PurchaseItemService;

@Controller
@RequestMapping("/api")
public class PurchaseItemController {

	private final PurchaseItemService purchaseItemService;

    public PurchaseItemController(PurchaseItemService purchaseItemService) {
        this.purchaseItemService = purchaseItemService;
    }

    @GetMapping("/purchaseItems/{id}")
    public ResponseEntity<List<PurchaseItem>> getPurchaseItemsByPurchaseId(@PathVariable Integer id) {
        List<PurchaseItem> purchaseItems = purchaseItemService.findByPurchaseId(id);
        return ResponseEntity.ok(purchaseItems);
    }

    @PostMapping
    public ResponseEntity<PurchaseItem> createPurchaseItem(@RequestBody PurchaseItem purchaseItem) {
        PurchaseItem createdPurchaseItem = purchaseItemService.save(purchaseItem);
        return new ResponseEntity<>(createdPurchaseItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/purcharseItem/{id}")
    public ResponseEntity<Void> deletePurchaseItem(@PathVariable Integer id) {
        purchaseItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/titleCounts")
    public List<TitleCountDto> getTitleCounts() {
        return purchaseItemService.getTitleCounts();
    }
}
