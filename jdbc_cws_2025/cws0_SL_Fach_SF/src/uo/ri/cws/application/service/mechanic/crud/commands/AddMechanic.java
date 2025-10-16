package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class AddMechanic {
    private static final String TMECHANICS_ADD = "insert into TMechanics"
            + "(id, nif, name, surname, version, "
            + "createdAt, updatedAt, entityState) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String TMECHANICS_FINDBYNIF = 
            "SELECT ID, NAME, SURNAME, nif, VERSION FROM TMECHANICS WHERE NIF = ?";

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

    	checkNifUnique(mechanic.nif);
    	return addMechanic(mechanic);
       

    }

	private MechanicDto addMechanic(MechanicDto mechanic2) {
		 // Process
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c.prepareStatement(TMECHANICS_ADD)) {
                pst.setString(1, mechanic.id);
                pst.setString(2, mechanic.nif);
                pst.setString(3, mechanic.name);
                pst.setString(4, mechanic.surname);
                pst.setLong(5, mechanic.version);
                pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                pst.setString(8, "ENABLED");               

                pst.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mechanic;
	}

	private void checkNifUnique(String nif) throws BusinessException {
		Optional<MechanicDto> existing = findByNif(nif);
		BusinessChecks.isFalse(existing.isPresent()
				, "There is already a mechanic with NIF " + nif);

	}
		

	private Optional<MechanicDto> findByNif(String nif) {
        Optional<MechanicDto> result = Optional.empty();
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_FINDBYNIF)) {
                pst.setString(1, nif);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        MechanicDto dto = new MechanicDto();
                        dto.id = rs.getString(1);
                        dto.name = rs.getString(2);
                        dto.surname = rs.getString(3);
                        dto.nif = rs.getString(4);
                        dto.version = rs.getLong(5);
                        result = Optional.of(dto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

	}

}
