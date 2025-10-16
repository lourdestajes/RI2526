package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class ListMechanicByNif implements Command<Optional<MechanicDto>> {

	private String nif;
	private MechanicGateway mGat = Factories.persistence.forMechanic();

	public ListMechanicByNif(String nif) {
		ArgumentChecks.isNotEmpty(nif, "Mechanic NIF cannot be null or empty");
		this.nif = nif;
	}

	public Optional<MechanicDto> execute() {
		Optional<MechanicRecord> optional = mGat.findByNif(nif);
		return MechanicAssembler.toDto(optional);
	}
}