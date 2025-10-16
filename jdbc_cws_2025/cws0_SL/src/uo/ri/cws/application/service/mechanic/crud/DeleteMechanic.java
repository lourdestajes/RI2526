package uo.ri.cws.application.service.mechanic.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class DeleteMechanic {

    private static final String TMECHANICS_DELETE = 
            "DELETE FROM TMECHANICS WHERE ID = ?";
    private String idMechanic;

    public DeleteMechanic(String idMechanic) {
        ArgumentChecks.isNotEmpty(idMechanic, 
                "The mechanic id cant be null or empty");
        this.idMechanic = idMechanic;
    }

    public void execute() throws BusinessException {

        // Process
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_DELETE)) {
                pst.setString(1, idMechanic);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}