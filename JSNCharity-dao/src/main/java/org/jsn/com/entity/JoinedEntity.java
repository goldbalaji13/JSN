package org.jsn.com.entity;

import java.time.LocalDate;

import org.jsn.dto.DrugDto;
import org.jsn.dto.UserDto;

public class JoinedEntity {
	public static JoinedEntity EntityFactory(Object[] objectArray) {
		JoinedEntity entity = new JoinedEntity();
		entity.drugDto = (DrugDto) objectArray[0];
		entity.userDetail = (UserDto) objectArray[1];
		return entity;
	}

	private DrugDto drugDto;

	private UserDto userDetail;

	private int soldQuantity = 0;

	public String getAddress() {
		return this.userDetail.getAddress();
	}

	public int getBatchNo() {
		return this.drugDto.getBatchNo();
	}

	public String getCity() {
		return this.userDetail.getCity();
	}

	public String getContactNo() {
		return this.userDetail.getContactNo();
	}

	public DrugDto getDrugDto() {
		return this.drugDto;
	}

	public String getDrugName() {
		return this.drugDto.getDrugName();
	}

	public LocalDate getExpiryDate() {
		return this.drugDto.getExpiryDate();
	}

	public String getName() {
		return this.userDetail.getName();
	}

	public int getQuantity() {
		return this.drugDto.getQuantity();
	}

	public int getSoldQuantity() {
		return this.soldQuantity;
	}

	public double getUnitPrice() {
		return this.drugDto.getUnitPrice();
	}

	public String getUserName() {
		return this.drugDto.getUserName();
	}

	public void sell(int quantity) {
		if (quantity <= this.getQuantity()) {
			this.soldQuantity = quantity + this.soldQuantity;
			this.drugDto.setQuantity(this.drugDto.getQuantity() - this.soldQuantity);
		}
	}

	public void takeBack() {
		this.drugDto.setQuantity(this.drugDto.getQuantity() + this.soldQuantity);
		this.soldQuantity = 0;
	}
}
