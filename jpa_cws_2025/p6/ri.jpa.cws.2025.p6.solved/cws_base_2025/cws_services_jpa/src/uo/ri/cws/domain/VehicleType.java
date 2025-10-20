package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class VehicleType {
	// natural attributes
	private String name;
	private double pricePerHour;

	// accidental attributes
	private Set<Vehicle> vehicles = new HashSet<>();

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


	@Override
	public int hashCode() {
		return Objects.hash(name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleType other = (VehicleType) obj;
		return Objects.equals(name, other.name);
	}

	
}
