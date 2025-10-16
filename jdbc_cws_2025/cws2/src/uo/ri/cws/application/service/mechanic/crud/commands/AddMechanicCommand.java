package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanicCommand implements Command<MechanicDto> {
	private DtoAssembler assembler = new DtoAssembler();
	
	private MechanicGateway mg = Factories.persistence.forMechanic();

	private MechanicDto dto;

	public AddMechanicCommand(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank( dto.nif );
		ArgumentChecks.isNotBlank( dto.name );
		ArgumentChecks.isNotBlank( dto.surname );
		this.dto = dto;
	}

	 
	public MechanicDto execute() throws BusinessException {
		assertNotRepeatedNif( dto.nif );
		dto.version = 1L;
		MechanicRecord m = assembler.toRecord(dto);
		
		mg.add(m);
		dto.id = m.id;
		return dto;
	}

	private void assertNotRepeatedNif(String nif) throws BusinessException {
		Optional<MechanicRecord> om = mg.findByNif(nif);
		BusinessChecks.isTrue(om.isEmpty(), 
				"There already exist a mechanic with this nif");
	}

}
