package uo.ri.cws.application.service.mechanic.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class UpdateMechanic {

    private static final String TMECHANICS_FINDBYID = 
            "select * from TMechanics where id = ?";
    private static final String TMECHANICS_UPDATE = 
            "update TMechanics set name = ?, surname = ?, version = version+1 "
            + "where id = ?";
    private MechanicDto mechanic;

    public UpdateMechanic(MechanicDto mechanic) {
        ArgumentChecks.isNotNull(mechanic, "The mechanic cannot be null");
        this.mechanic = mechanic;
    }

    public void execute() throws BusinessException {

        // check mechanic exists
        checkMechanicExists(mechanic.id);
        // update
        updateMechanic(mechanic.id, mechanic.name, mechanic.surname);

    }

    private void updateMechanic(String id, String name, String surname) {

        // Process
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c.prepareStatement(TMECHANICS_UPDATE)) {
                pst.setString(1, name);
                pst.setString(2, surname);
                pst.setString(3, id);

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkMechanicExists(String id) throws BusinessException {
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c.prepareStatement(TMECHANICS_FINDBYID)) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) {
                        throw new BusinessException("Mechanic does not exist");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}