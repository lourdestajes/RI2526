package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class AddMechanic {

	private MechanicGateway mechanicGateway = Factories.persistence.forMechanic();
	private MechanicDto mechanic;

	public AddMechanic(MechanicDto mechanic) {
		ArgumentChecks.isNotNull(mechanic, "null argument is not allowed");
		ArgumentChecks.isNotBlank(mechanic.nif, "NIF cannot be null or blank");
		ArgumentChecks.isNotBlank(mechanic.name, "Name cannot be null or blank");
		ArgumentChecks.isNotBlank(mechanic.surname, "Surname cannot be null or blank");
		this.mechanic = mechanic;
		this.mechanic.id = UUID.randomUUID().toString();
		this.mechanic.version = 1L;
	}

	public MechanicDto execute() throws BusinessException {
		MechanicDto result = null;
		try (Connection c = Jdbc.createThreadConnection()) {
			try {
				c.setAutoCommit(false);
				checkNifUnique(mechanic.nif);
				result = addMechanic(mechanic);
				c.commit();
			} catch (Exception e) {
				c.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private MechanicDto addMechanic(MechanicDto m) {
		// Process
		mechanicGateway.add(MechanicAssembler.toRecord(m));
		return mechanic;
	}

	private void checkNifUnique(String nif) throws BusinessException {
		Optional<MechanicRecord> existing = mechanicGateway.findByNif(nif);
		BusinessChecks.isFalse(existing.isPresent(), "There is already a mechanic with NIF " + nif);
	}

}
