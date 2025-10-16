package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class ListMechanicById {

	private String id;

	public ListMechanicById(String id) {
		ArgumentChecks.isNotEmpty(id, "Mechanic NIF cannot be null or empty");
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		Optional<MechanicDto> result = Optional.empty();
		try (Connection c = Jdbc.createThreadConnection()) {
			try {
				c.setAutoCommit(false);

				Optional<MechanicRecord> optional = Factories.persistence.forMechanic().findById(id);
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