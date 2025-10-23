package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVehicleTypes")
public class VehicleType extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String name;
	private double pricePerHour;

	// accidental attributes
	@OneToMany(mappedBy="vehicleType") 
	private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType() {
		// for JPA
	}
	
	public VehicleType(String name) {
		this(name, 0.0);
	}


	public VehicleType(String name, double price) {
		ArgumentChecks.isNotBlank( name, "The vehicle type name cannot be null" );
        ArgumentChecks.isTrue( price > 0, "The price per hour cannot be negative" );
        this.name = name;
        this.pricePerHour = price;
	}

	

	public String getName() {
		return name;
	}


	public double getPricePerHour() {
		return pricePerHour;
	}


	public Set<Vehicle> getVehicles() {
		return new HashSet<>( vehicles );
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}


	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour + "]";
	}

}
