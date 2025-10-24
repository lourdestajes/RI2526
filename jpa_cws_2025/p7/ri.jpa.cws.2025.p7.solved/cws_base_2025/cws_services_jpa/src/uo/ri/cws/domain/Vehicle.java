package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVehicles")
public class Vehicle extends BaseEntity {
	@Column(unique=true)
	private String plateNumber;
	private String make;
	private String model;
	
	// Atributos accidentales
	@ManyToOne private Client client;
	@ManyToOne private VehicleType vehicleType;
	@OneToMany(mappedBy="vehicle") private Set<WorkOrder> workOrders = new HashSet<>();

	Vehicle() {
		// for JPA
	}
	
	public Vehicle(String plateNumber, String make, String model) {
		ArgumentChecks.isNotBlank( plateNumber, "Plate number cannot be null" );
		ArgumentChecks.isNotBlank( make, "Make cannot be null" );
		ArgumentChecks.isNotBlank( model, "Model cannot be null" );
		
		this.plateNumber = plateNumber;
		this.make = make;
		this.model = model;
	}

	public Vehicle(String plateNumber) {
		this(plateNumber, "no-make", "no-model");
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

	public Client getClient() {
		return client;
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
	
	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model + "]";
	}
}
