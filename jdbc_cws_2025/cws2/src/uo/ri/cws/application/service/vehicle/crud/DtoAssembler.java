package uo.ri.cws.application.service.vehicle.crud;


import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;

public class DtoAssembler {

	public static VehicleDto toDto(VehicleRecord record) {
		VehicleDto dto = new VehicleDto();
		dto.id = record.id;
		dto.version = record.version;
		
		dto.plate = record.plate;
		dto.clientId = record.clientId;
		dto.make = record.make;
		dto.vehicleTypeId = record.vehicleTypeId;
		dto.model = record.model;

		return dto;
	}

}
