package org.jsn.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drug")
public class DrugDto implements Serializable {
	@Id
	private String userName;
	@Id
	private String drugName;
	@Id
	private int batchNo;

	private int quantity;

	private double unitPrice;

	private LocalDate expiryDate;

	public int getBatchNo() {
		return this.batchNo;
	}

	public String getDrugName() {
		return this.drugName;
	}

	public LocalDate getExpiryDate() {
		return this.expiryDate;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
