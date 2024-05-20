package org.e2e.e2e.auth.dto;

import org.e2e.e2e.vehicle.dto.VehicleBasicDto;

import lombok.Data;

@Data
public class RegisterRequest {
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    Boolean isDriver=false;
    Category category;
    VehicleBasicDto vehicle;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(Boolean isDriver) {
		this.isDriver = isDriver;
	}

	public VehicleBasicDto getVehicle() {
		return vehicle;
	}
	public void setVehicle(VehicleBasicDto vehicle) {
		this.vehicle = vehicle;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
}