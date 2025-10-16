package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanicCommand implements Command<Void> {
	private MechanicGateway mg = Factories.persistence.forMechanic();

	private MechanicDto dto;

	public UpdateMechanicCommand(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank( dto.nif );
		ArgumentChecks.isNotBlank( dto.name );
		ArgumentChecks.isNotBlank( dto.surname );

		this.dto = dto;
	}

	 
	public Void execute() throws BusinessException {
		Optional<MechanicRecord> om = mg.findById( dto.id );
		BusinessChecks.exists(om, "The mechanic does not exist");

		MechanicRecord m = om.get();
		BusinessChecks.hasVersion(m.version, dto.version);

		m.name = dto.name;
		m.surname = dto.surname;
		mg.update(m);

		return null;
	}

}
