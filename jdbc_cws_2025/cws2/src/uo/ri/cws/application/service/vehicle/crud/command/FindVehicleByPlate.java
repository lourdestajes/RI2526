package uo.ri.cws.application.service.vehicle.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.vehicle.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindVehicleByPlate implements Command<Optional<VehicleDto>> {

	private String plate;
	private VehicleGateway repo = Factories.persistence.forVehicle();
	
	public FindVehicleByPlate(String plate) {
		ArgumentChecks.isNotBlank( plate );
		this.plate = plate;
	}

	 
	public Optional<VehicleDto> execute() throws BusinessException {
		Optional<VehicleRecord> ov = repo.findByPlate( plate );
		return ov.map( v  -> DtoAssembler.toDto( v ) );
	}

}
