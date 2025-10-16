package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class UpdateMechanic {

	private static final String TMECHANICS_FINDBYID = "select * from TMechanics where id = ?";
	private static final String TMECHANICS_UPDATE = "update TMechanics set name = ?, surname = ?, "
			+ "version = version + 1, updatedat = ?" + "where id = ?";
	private MechanicDto received;

	public UpdateMechanic(MechanicDto mechanic) {
		ArgumentChecks.isNotNull(mechanic, "The mechanic cannot be null");
		ArgumentChecks.isNotBlank(mechanic.id, "The mechanic id cannot be null");
		ArgumentChecks.isNotBlank(mechanic.nif, "The mechanic nif cannot be null");
		ArgumentChecks.isNotBlank(mechanic.name, "The mechanic name cannot be null");
		ArgumentChecks.isNotBlank(mechanic.surname, "The mechanic surname cannot be null");
		this.received = mechanic;
	}

	public void execute() throws BusinessException {

		// check mechanic exists
		MechanicDto readFromDatabase = checkMechanicExists(received.id);
		BusinessChecks.hasVersion(received.version, readFromDatabase.version, "Staled data");

		// update
		updateMechanic(received.id, received.name, received.surname);

	}

	private void updateMechanic(String id, String name, String surname) {

		// Process
		try (Connection c = Jdbc.createThreadConnection()) {
			try (PreparedStatement pst = c.prepareStatement(TMECHANICS_UPDATE)) {
				pst.setString(1, name);
				pst.setString(2, surname);
				pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pst.setString(4, id);

				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private MechanicDto checkMechanicExists(String id) throws BusinessException {
		try (Connection c = Jdbc.createThreadConnection()) {
			try (PreparedStatement pst = c.prepareStatement(TMECHANICS_FINDBYID)) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					BusinessChecks.isTrue(rs.next(), "Mechanic does not exist");

					MechanicDto m = new MechanicDto();
					m.id = rs.getString("id");
					m.name = rs.getString("name");
					m.surname = rs.getString("surname");
					m.version = rs.getLong("version");
					return m;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}