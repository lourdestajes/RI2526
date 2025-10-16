package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.jdbc.Jdbc;

public class ListAllMechanics {

	public List<MechanicDto> execute() {
		List<MechanicDto> result = new ArrayList<>();
		try (Connection c = Jdbc.createThreadConnection()) {
			try {
				c.setAutoCommit(false);

				List<MechanicRecord> records = Factories.persistence.forMechanic().findAll();
				result = MechanicAssembler.toDto(records);
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