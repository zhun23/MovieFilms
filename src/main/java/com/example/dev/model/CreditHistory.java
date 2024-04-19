package com.example.dev.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.dev.utilities.References;

import lombok.Data;
/*

@Table(name=References.CREDITHISTORY_TABLE_NAME)
@Entity
@Data
public class CreditHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message = "La fecha de recarga no puede estar vacía")
	@Column(name="date")
	private LocalDate date;
	
	@NotNull(message = "La cantidad de recarga no puede estar vacía")
	@Column(name="amount")
	private int amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
}
*/
