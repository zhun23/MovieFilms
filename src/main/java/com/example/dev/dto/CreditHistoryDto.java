package com.example.dev.dto;

import java.time.LocalDateTime;

import com.example.dev.model.CreditHistory;

public class CreditHistoryDto {
	private int historyid;
	private String userNickname;
	private Integer userid;
    private LocalDateTime date;
    private Integer amount;
    private Integer totalCredit;
    private String recharge;
    private Boolean buy;

    public CreditHistoryDto(CreditHistory creditHistory) {
        this.historyid = creditHistory.getHistoryid();
        this.userNickname = creditHistory.getUserCt().getNickname();
        this.userid = creditHistory.getUserCt().getUserid();
        this.date = creditHistory.getDate();
        this.amount = creditHistory.getAmount();
        this.totalCredit = creditHistory.getTotalCredit();
        this.recharge = creditHistory.getAction();
        this.buy = creditHistory.isBuy();
    }

	public int getHistoryid() {
		return historyid;
	}

	public void setHistoryid(int historyid) {
		this.historyid = historyid;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(Integer totalCredit) {
		this.totalCredit = totalCredit;
	}

	public String getRecharge() {
		return recharge;
	}

	public void setRecharge(String recharge) {
		this.recharge = recharge;
	}

	public Boolean getBuy() {
		return buy;
	}

	public void setBuy(Boolean buy) {
		this.buy = buy;
	}


}
