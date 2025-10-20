package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Vehicle {
	private String plateNumber;
	private String make;
	private String model;
	
	// Atributos accidentales
	private Client client;
	private VehicleType vehicleType;
	private Set<WorkOrder> workOrders = new HashSet<>();

	public Vehicle(String plateNumber, String make, String model) {
		ArgumentChecks.isNotBlank( plateNumber, "Plate number cannot be null" );
		ArgumentChecks.isNotBlank( make, "Make cannot be null" );
		ArgumentChecks.isNotBlank( model, "Model cannot be null" );
		
		this.plateNumber = plateNumber;
		this.make = make;
		this.model = model;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	@Override
	public int hashCode() {
		return Objects.hash(plateNumber);
	}

	public Client getClient() {
		return client;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(plateNumber, other.plateNumber);
	}

	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model + "]";
	}

	void _setClient(Client client) {
		this.client = client;
	}
	
	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	Set<WorkOrder> _getWorkOrders() {
		return this.workOrders ;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}
	
}
