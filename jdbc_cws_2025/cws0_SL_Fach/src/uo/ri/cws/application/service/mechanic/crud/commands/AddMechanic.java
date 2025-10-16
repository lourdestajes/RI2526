package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class AddMechanic {
    private static final String TMECHANICS_ADD = 
            "insert into TMechanics(id, nif, name, surname, version) "
            + "values (?, ?, ?, ?, ?)";

    private MechanicDto mechanic;

    public AddMechanic(MechanicDto mechanic) {
        ArgumentChecks.isNotNull(mechanic, "null argument is not allowed");
        ArgumentChecks.isNotEmpty(mechanic.nif, "NIF cannot be null or empty");
        ArgumentChecks.isNotEmpty(mechanic.name, "Name cannot be null or empty");
        ArgumentChecks.isNotEmpty(mechanic.surname, "Surname cannot be null or empty");
        this.mechanic = mechanic;
        this.mechanic.id = UUID.randomUUID().toString();
        this.mechanic.version = 1L;

    }

    public MechanicDto execute() throws BusinessException {

        // Process
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c.prepareStatement(TMECHANICS_ADD)) {
                pst.setString(1, mechanic.id);
                pst.setString(2, mechanic.nif);
                pst.setString(3, mechanic.name);
                pst.setString(4, mechanic.surname);
                pst.setLong(5, mechanic.version);

                pst.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mechanic;

    }

}
