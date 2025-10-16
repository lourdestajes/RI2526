package uo.ri.cws.application.persistence.vehicle;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;

public interface VehicleGateway extends Gateway<VehicleRecord> {

	public class VehicleRecord extends BaseRecord {
		public String plate;
		public String clientId;
		public String make;
		public String vehicleTypeId;
		public String model;

	}

	public Optional<VehicleRecord> findByPlate(String plate) throws PersistenceException; 	
	
}
