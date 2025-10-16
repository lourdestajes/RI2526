package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

	 
	public void add(InvoiceRecord t) throws PersistenceException {
		try {
			Connection connection = Jdbc.getCurrentConnection();
			try (PreparedStatement pst = connection.prepareStatement(Queries.getSQLSentence("TINVOICES_INSERT"))) {
				pst.setString(1, t.id);
				pst.setLong(2, t.number);
				pst.setDate(3, Date.valueOf(t.date));
				pst.setDouble(4, t.vat);
				pst.setDouble(5, t.amount);
				pst.setString(6, t.status);
				pst.setLong(7, t.version);
				pst.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
				pst.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
				pst.setString(10, "ENABLED");
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	 
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	 
	public void update(InvoiceRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	 
	public Optional<InvoiceRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	 
	public List<InvoiceRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public long findNextInvoiceNumber() {

		try {
			Connection connection = Jdbc.getCurrentConnection();
			try (PreparedStatement pst = connection
					.prepareStatement(Queries.getSQLSentence("TINVOICES_FINDNEXTNUMBER"))) {
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return rs.getLong(1) + 1;
					}
				}
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		return 1L; // Si no hay facturas previas, empezamos desde 1
	}

}
