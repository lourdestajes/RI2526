package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

    public void add(InvoiceRecord t) throws PersistenceException {
        new JdbcTemplate<InvoiceRecord>()
                                         .forNamedSql("TINVOICES_ADD")
                                         .withBinder(pst -> {
                                             pst.setString(1, t.id);
                                             pst.setLong(2, t.number);
                                             pst.setDate(3,
                                                     Date.valueOf(t.date));
                                             pst.setDouble(4, t.vat);
                                             pst.setDouble(5, t.amount);
                                             pst.setString(6, t.state);
                                             pst.setTimestamp(7,
                                                     Timestamp.valueOf(
                                                             t.createdAt));
                                             pst.setTimestamp(8,
                                                     Timestamp.valueOf(
                                                             t.updatedAt));

                                         })
                                         .execute();
    }

    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void update(InvoiceRecord t) throws PersistenceException {
        return;
    }

    public Optional<InvoiceRecord> findById(String id)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<InvoiceRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Optional<InvoiceRecord> findByNumber(Long number) {
        // TODO Auto-generated method stub
        return null;
    }

    public Long getNextInvoiceNumber() throws PersistenceException {
        return new JdbcTemplate<Long>()
                                       .forNamedSql(
                                               "TINVOICES_GETNEXTINVOICENUMBER")
                                       .withRowMapper(rs -> rs.getLong(1) + 1)
                                       .getOptionalResult()
                                       .orElse(1L);
    }

    @Override
    public List<InvoiceRecord> findByVehicleId(String id) {
        try {
            Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c.prepareStatement(
                    Queries.get("TINVOICES_FIND_BY_VEHICLE"))) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    return new RecordAssembler().toRecordList(rs);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<InvoiceRecord> findUnapidInvoices() {
        try {
            Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c.prepareStatement(
                    Queries.get("TINVOICES_FIND_UNPAID"))) {
                try (ResultSet rs = pst.executeQuery()) {
                    return new RecordAssembler().toRecordList(rs);
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
