package com.example.dev.dto;

import java.time.LocalDateTime;

import com.example.dev.model.CreditHistory;

import lombok.Data;

@Data
public class CreditHistoryDto {
	private int id;
	private String userNickname;
	private int userId;
    private LocalDateTime date;
    private int amount;
    private int totalCredit;
    private String recharge;

    public CreditHistoryDto(CreditHistory creditHistory) {
        this.id = creditHistory.getId();
        this.userNickname = creditHistory.getUser().getNickname();
        this.userId = creditHistory.getUser().getId();
        this.date = creditHistory.getDate();
        this.amount = creditHistory.getAmount();
        this.totalCredit = creditHistory.getTotalCredit();
        this.recharge = creditHistory.getRecharge();
    }
}
