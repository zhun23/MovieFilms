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
import lombok.Data;


@Table(name=References.CREDITHISTORY_TABLE_NAME)
@Entity
@Data
public class CreditHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="creditid")
	private int id;
	
	
	@NotNull(message = "La fecha de recarga no puede estar vacía")
	@Column(name="date")
	private LocalDateTime date;
	
	@NotNull(message = "La cantidad de recarga no puede estar vacía")
	@Column(name="amount")
	private int amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "userid")
	private User user;
	
	@Column(name="nickname")
	private String nickname;
	
	@NotNull(message = "El crédito total no puede estar vacío")
    @Column(name = "totalcredit")
    private int totalCredit;
	
	@Column(name="recharge")
	private String recharge;
	
	@Column(name="rent")
	private int rent;
}

