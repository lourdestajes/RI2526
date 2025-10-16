package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto received;
	private MechanicGateway mGat = Factories.persistence.forMechanic();

	public UpdateMechanic(MechanicDto mechanic) {
		ArgumentChecks.isNotNull(mechanic, "The mechanic cannot be null");
		ArgumentChecks.isNotBlank(mechanic.id, "The mechanic id cannot be null");
		ArgumentChecks.isNotBlank(mechanic.nif, "The mechanic nif cannot be null");
		ArgumentChecks.isNotBlank(mechanic.name, "The mechanic name cannot be null");
		ArgumentChecks.isNotBlank(mechanic.surname, "The mechanic surname cannot be null");
		this.received = mechanic;
	}

	public Void execute() throws BusinessException {
		// check mechanic exists
		MechanicRecord readFromDatabase = checkMechanicExists(received.id);
		BusinessChecks.hasVersion(received.version, readFromDatabase.version, "Staled data");

		// update
		mGat.update(MechanicAssembler.toRecord(received));
		return null;
	}

	private MechanicRecord checkMechanicExists(String id) throws BusinessException {
		Optional<MechanicRecord> optional = mGat.findById(id);
		BusinessChecks.isTrue(optional.isPresent(), "Mechanic does not exist");
		return optional.get();
	}
}