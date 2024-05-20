package org.e2e.e2e.driver.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.e2e.e2e.coordinate.domain.Coordinate;
import org.e2e.e2e.ride.domain.Ride;
import org.e2e.e2e.user.domain.User;
import org.e2e.e2e.vehicle.domain.Vehicle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Driver extends User {

    @Column(nullable = false)
    private Category category;

    @Column
    private String hexAdress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToMany(mappedBy = "driver")
    private List<Ride> rides;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getHexAdress() {
		return hexAdress;
	}

	public void setHexAdress(String hexAdress) {
		this.hexAdress = hexAdress;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

}
