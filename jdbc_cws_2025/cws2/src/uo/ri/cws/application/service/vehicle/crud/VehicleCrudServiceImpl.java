package uo.ri.cws.application.service.vehicle.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.command.FindVehicleByPlate;
import uo.ri.util.exception.BusinessException;


public class VehicleCrudServiceImpl implements VehicleCrudService {

	private CommandExecutor executor = new CommandExecutor();

	 
	public Optional<VehicleDto> findByPlate(String plate)
			throws BusinessException {
		return executor.execute(new FindVehicleByPlate(plate));
	}

}
