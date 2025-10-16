package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.util.exception.BusinessException;

public class FindAllCommand implements Command<List<MechanicDto>> {
	private MechanicGateway mg = Factories.persistence.forMechanic();
	private DtoAssembler assembler = new DtoAssembler();

	 
	public List<MechanicDto> execute() throws BusinessException {
		List<MechanicRecord> list = mg.findAll(); 
		return assembler.toDtoList(list);
	}

}
