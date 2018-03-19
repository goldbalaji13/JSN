package org.jsn.com.entity;

import org.jsn.dto.UserDto;
import org.jsn.enums.Role;

import lombok.Data;

@Data
public class UserEntity {

	public static UserEntity formEntity(UserDto dto) {
		UserEntity entity = new UserEntity();
		entity.setUserName(dto.getUserName());
		entity.setRole(dto.getRole());
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		entity.setCity(dto.getCity());
		entity.setContactNo(dto.getContactNo());
		return entity;
	}

	private String userName;

	private String password;

	private Role role;

	private String name;

	private String address;

	private String city;

	private String contactNo;

	public UserDto formDto() {
		UserDto dto = new UserDto();
		dto.setUserName(this.userName);
		dto.setPassword(this.password);
		dto.setRole(this.role);
		dto.setName(this.name);
		dto.setAddress(this.address);
		dto.setCity(this.city);
		dto.setContactNo(this.contactNo);
		return dto;
	}

}
