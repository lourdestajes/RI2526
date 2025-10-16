package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

     
    public void add(InterventionRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

     
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

     
    public void update(InterventionRecord t) throws PersistenceException {
        // TODO Auto-generated method stub

    }

     
    public Optional<InterventionRecord> findById(String id)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

     
    public List<InterventionRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

     
    public List<InterventionRecord> findByMechanicId(String id) {
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence(
                            "TINTERVENTIONS_FIND_BY_MECHANICID"))) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    return InterventionAssembler.toInterventionList(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
