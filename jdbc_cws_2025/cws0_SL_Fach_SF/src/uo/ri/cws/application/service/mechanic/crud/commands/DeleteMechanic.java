package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class DeleteMechanic {

    private static final String TMECHANICS_DELETE = 
            "DELETE FROM TMECHANICS WHERE ID = ?";
    private static final String TMECHANICS_FINDBYID = 
    		"SELECT ID, NAME, SURNAME, nif, VERSION FROM TMECHANICS WHERE ID = ?";
    private static final String TWORKORDERS_FIND_BY_MECHANICID = 
			"SELECT * FROM TWORKORDERS WHERE MECHANIC_ID = ?";
	private static final String TINTERVENTIONS_FIND_BY_MECHANICID = 
			"SELECT * FROM TINTERVENTIONS WHERE MECHANIC_ID = ?";


    private String idMechanic;

    public DeleteMechanic(String idMechanic) {
        ArgumentChecks.isNotEmpty(idMechanic, 
                "The mechanic id cant be null or empty");
        this.idMechanic = idMechanic;
    }

    public void execute() throws BusinessException {

    	checkIfMechanicExists(idMechanic);
    	checksIfMechanicCanBeDeleted(idMechanic);
    	deleteMechanic(idMechanic);

    }

	private void deleteMechanic(String id) {
        // Process
        try (Connection c = Jdbc.createThreadConnection();) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_DELETE)) {
                pst.setString(1, id);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }		
	}

	private void checksIfMechanicCanBeDeleted(String id) throws BusinessException {
		checkIfMechanicHasWorkOrders(id);
		chekkIfMechanicHasInterventions(id);

	}

	private void chekkIfMechanicHasInterventions(String id) throws BusinessException {
		try (Connection c = Jdbc.createThreadConnection()) {
			try (PreparedStatement pst = c
					.prepareStatement(TINTERVENTIONS_FIND_BY_MECHANICID)) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					BusinessChecks.isTrue(!rs.next(),
							"The mechanic with id " + idMechanic
									+ " cannot be deleted because it has associated work orders");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}					
	}

	private void checkIfMechanicHasWorkOrders(String id) throws BusinessException {
		try (Connection c = Jdbc.createThreadConnection()) {
			try (PreparedStatement pst = c
					.prepareStatement(TWORKORDERS_FIND_BY_MECHANICID)) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					BusinessChecks.isTrue(!rs.next(),
							"The mechanic with id " + idMechanic
									+ " cannot be deleted because it has associated work orders");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}			
	}

	private void checkIfMechanicExists(String id) throws BusinessException {
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_FINDBYID)) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    BusinessChecks.isTrue(rs.next(), 
								"The mechanic with id " + id
										+ " does not exist");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}

}