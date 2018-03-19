package org.jsn.dto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jsn.enums.Role;

@Entity
@Table(name = "users")
public class UserDto {

	private static transient final String SALT = "password-hash";

	@Id
	private String userName;

	private String password;

	private Role role;

	private String name;

	private String address;

	private String city;

	private String contactNo;

	private String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			for (byte b : hashedBytes) {
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}

		return hash.toString();
	}

	public String getAddress() {
		return this.address;
	}

	public String getCity() {
		return this.city;
	}

	public String getContactNo() {
		return this.contactNo;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public Role getRole() {
		return this.role;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = this.generateHash(password + SALT);
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserDto [userName=" + this.userName + ", role=" + this.role + ", name=" + this.name + ", address="
				+ this.address + ", city=" + this.city + ", contactNo=" + this.contactNo + "]";
	}
}
