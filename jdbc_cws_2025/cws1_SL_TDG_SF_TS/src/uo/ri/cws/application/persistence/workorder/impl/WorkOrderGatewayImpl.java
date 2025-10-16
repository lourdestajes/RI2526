package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	 
	public void add(WorkorderRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	 
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	 
	public void update(WorkorderRecord t) throws PersistenceException {
		try {
			Connection connection = Jdbc.getCurrentConnection();
			try (PreparedStatement pst = connection.prepareStatement(Queries.getSQLSentence("TWORKORDERS_UPDATE"))) {
				pst.setDouble(1, t.amount);
				pst.setString(2, t.state);
				pst.setTimestamp(3, Timestamp.valueOf(t.updatedAt));
				pst.setString(4, t.invoiceId);
				pst.setString(5, t.mechanicId);
				pst.setString(6, t.id);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	 
	public Optional<WorkorderRecord> findById(String id) throws PersistenceException {
		try {
			Connection c = Jdbc.getCurrentConnection();
			try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_BY_ID"))) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					return WorkorderAssembler.toWorkorderRecord(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	 
	public List<WorkorderRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public List<WorkorderRecord> findByMechanicId(String id) {
		try {
			Connection c = Jdbc.getCurrentConnection();
			try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_BY_MECHANICID"))) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					return WorkorderAssembler.toWorkorderList(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	 
	public List<WorkorderRecord> findNotInvoicedByClientNif(String nif) {
		try {
			Connection c = Jdbc.getCurrentConnection();
			try (PreparedStatement pst = c
					.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED_BY_CLIENT"))) {
				pst.setString(1, nif);
				try (ResultSet rs = pst.executeQuery()) {
					return WorkorderAssembler.toWorkorderList(rs);
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
