package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

public class ListMechanicByNif {

	private String nif;
	private MechanicGateway mGat = Factories.persistence.forMechanic();

	public ListMechanicByNif(String nif) {
		ArgumentChecks.isNotEmpty(nif, "Mechanic NIF cannot be null or empty");
		this.nif = nif;
	}

	public Optional<MechanicDto> execute() {
		Optional<MechanicDto> result = Optional.empty();
		try (Connection c = Jdbc.createThreadConnection()) {
			try {
				c.setAutoCommit(false);

				Optional<MechanicRecord> optional = mGat.findByNif(nif);
				result = MechanicAssembler.toDto(optional);
			} catch (Exception e) {
				c.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}