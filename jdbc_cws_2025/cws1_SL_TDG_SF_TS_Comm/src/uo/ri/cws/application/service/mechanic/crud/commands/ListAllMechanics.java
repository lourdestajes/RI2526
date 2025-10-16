package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;

public class ListAllMechanics implements Command<List<MechanicDto>> {

	public List<MechanicDto> execute() {
		List<MechanicRecord> records = Factories.persistence.forMechanic().findAll();
		return MechanicAssembler.toDto(records);
	}
}