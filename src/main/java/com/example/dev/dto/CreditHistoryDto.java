package com.example.dev.dto;

import java.time.LocalDateTime;

import com.example.dev.model.CreditHistory;

import lombok.Data;

@Data
public class CreditHistoryDto {
	private int historyid;
	private String userNickname;
	private int userid;
    private LocalDateTime date;
    private int amount;
    private int totalCredit;
    private String recharge;
    private int rent;

    public CreditHistoryDto(CreditHistory creditHistory) {
        this.historyid = creditHistory.getHistoryid();
        this.userNickname = creditHistory.getUserCt().getNickname();
        this.userid = creditHistory.getUserCt().getUserid();
        this.date = creditHistory.getDate();
        this.amount = creditHistory.getAmount();
        this.totalCredit = creditHistory.getTotalCredit();
        this.recharge = creditHistory.getRecharge();
        this.rent = creditHistory.getRent();
    }
}
