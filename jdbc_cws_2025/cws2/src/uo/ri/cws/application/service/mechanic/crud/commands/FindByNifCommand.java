package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByNifCommand implements Command<Optional<MechanicDto>> {
	private MechanicGateway mg = Factories.persistence.forMechanic();
	private DtoAssembler assembler = new DtoAssembler();

	private String nif;

	public FindByNifCommand(String nif) {
		ArgumentChecks.isNotNull(nif);
		this.nif = nif;
	}

	 
	public Optional<MechanicDto> execute() throws BusinessException {
		Optional<MechanicRecord> om = mg.findByNif(nif);
		return om.map( assembler::toDto );
	}


}
