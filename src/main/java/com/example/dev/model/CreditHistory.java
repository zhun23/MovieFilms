package com.example.dev.model;

import java.time.LocalDateTime;

import com.example.dev.utilities.References;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name=References.CREDITHISTORY_TABLE_NAME)
@Entity
public class CreditHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column //(name="historyid")
	private Integer historyid;


	@NotNull(message = "La fecha de recarga no puede estar vacía")
	@Column(name="date")
	private LocalDateTime date;

	@NotNull(message = "La cantidad de recarga no puede estar vacía")
	@Column(name="amount")
	private Integer amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", referencedColumnName = "userid")
	private UserCt userCt;

	@Column(name="usernickname")
	private String usernickname;

	@NotNull(message = "El crédito total no puede estar vacío")
    @Column(name = "totalcredit")
    private Integer totalCredit;

	@Column(name="action")
	private String action;

	@Column(name="buy")
	private Boolean buy;

	public CreditHistory() {
		super();
	}

	public CreditHistory(LocalDateTime date, Integer amount, UserCt userCt, String usernickname, Integer totalCredit, String action, Boolean buy) {
		super();
		this.date = date;
		this.amount = amount;
		this.userCt = userCt;
		this.usernickname = usernickname;
		this.totalCredit = totalCredit;
		this.action = action;
		this.buy = buy;
	}

	public CreditHistory(Integer historyid, LocalDateTime date, Integer amount, UserCt userCt, String usernickname, Integer totalCredit, String action, Boolean buy) {
		super();
		this.historyid = historyid;
		this.date = date;
		this.amount = amount;
		this.userCt = userCt;
		this.usernickname = usernickname;
		this.totalCredit = totalCredit;
		this.action = action;
		this.buy = buy;
	}

	public Integer getHistoryid() {
		return historyid;
	}

	public void setHistoryid(Integer historyid) {
		this.historyid = historyid;
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

	public UserCt getUserCt() {
		return userCt;
	}

	public void setUserCt(UserCt userCt) {
		this.userCt = userCt;
	}

	public String getUsernickname() {
		return usernickname;
	}

	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}

	public Integer getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(Integer totalCredit) {
		this.totalCredit = totalCredit;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean isBuy() {
		return buy;
	}

	public void setBuy(Boolean buy) {
		this.buy = buy;
	}
}

