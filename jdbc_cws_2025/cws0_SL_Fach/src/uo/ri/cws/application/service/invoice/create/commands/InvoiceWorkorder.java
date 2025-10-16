package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.math.Rounds;

public class InvoiceWorkorder {

    private static final String TINVOICES_ADD = "INSERT INTO TInvoices(id, number, date, vat, amount, state, version) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String TINVOICES_FINDNEXTNUMBER = "SELECT MAX(number) FROM TInvoices";

    private static final String TWORKORDERS_FINDAMOUNT = "SELECT amount FROM TWorkOrders WHERE id = ?";
    private static final String TWORKORDERS_FINDID = "SELECT id FROM TWorkOrders WHERE id = ?";
    private static final String TWORKORDERS_FINDSTATE = "SELECT state FROM TWorkOrders WHERE id = ?";
    private static final String TWORKORDERS_UPDATEINVOICEID = "UPDATE TWorkOrders SET invoice_id = ? WHERE id = ?";
    private static final String TWORKORDERS_UPDATESTATE = "UPDATE TWorkOrders SET state = 'INVOICED' WHERE id = ?";
    private static final String TWORKORDERS_UPDATEVERSION = "update TWorkOrders set version=version+1 where id = ?";
    private List<String> ids;

    public InvoiceWorkorder(List<String> workOrderIds) {
        ArgumentChecks.isNotNull(workOrderIds,
                "The list of work order ids cannot be null");
        this.ids = workOrderIds;
    }

    public InvoiceDto execute() throws BusinessException {
        InvoiceDto result = null;
        try (Connection ignored = Jdbc.createThreadConnection()) {

            if (!checkWorkOrdersExist(ids))
                throw new BusinessException("Workorder does not exist");
            if (!checkWorkOrdersFinished(ids))
                throw new BusinessException("Workorder is not finished yet");

            long numberInvoice = generateInvoiceNumber();
            LocalDate dateInvoice = LocalDate.now();
            double amount = calculateTotalInvoice(ids); // vat not included
            double vat = vatPercentage(dateInvoice);
            double total = amount * (1 + vat / 100); // vat included
            total = Rounds.toCents(total);

            String idInvoice = createInvoice(numberInvoice, dateInvoice, vat, total);
            linkWorkordersToInvoice(idInvoice, ids);
            markWorkOrderAsInvoiced(ids);
            updateVersion(ids);
            result = new InvoiceDto();
            result.id = idInvoice;
            result.number = numberInvoice;
            result.date = dateInvoice;
            result.amount = amount;
            result.vat = vat;
            result.state = "NOT_YET_PAID";
            result.version = 1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void updateVersion(List<String> workOrderIds) throws SQLException {
        Connection c = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = c.prepareStatement(TWORKORDERS_UPDATEVERSION)) {
            for (String workOrderID : workOrderIds) {
                pst.setString(1, workOrderID);
                pst.executeUpdate();
            }
        }
    }


    /*
     * checks whether every work order exist
     */
    private boolean checkWorkOrdersExist(List<String> workOrderIDS) throws SQLException {

        Connection c = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = c.prepareStatement(TWORKORDERS_FINDID)) {
            for (String workOrderID : workOrderIDS) {
                pst.setString(1, workOrderID);
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next())
                        return false; // Si no encuentra la orden de trabajo
                }
            }
        }
        return true;
    }

    /*
     * checks whether every work order id is FINISHED
     */
    private boolean checkWorkOrdersFinished(List<String> workOrderIDs) throws SQLException {
        Connection connection = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = connection.prepareStatement(TWORKORDERS_FINDSTATE)) {
            for (String id : workOrderIDs) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        String status = rs.getString("state");
                        if (!"FINISHED".equalsIgnoreCase(status)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private long generateInvoiceNumber() throws SQLException {
        Connection connection = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = connection.prepareStatement(TINVOICES_FINDNEXTNUMBER)) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1) + 1;
                }
            }
        }
        return 1L; // Si no hay facturas previas, empezamos desde 1
    }

    /*
     * Compute total amount of the invoice (as the total of individual work orders'
     * amount
     */
    private double calculateTotalInvoice(List<String> workOrderIDs) throws SQLException {
        Connection connection = Jdbc.getCurrentConnection();

        double total = 0.0;
        try (PreparedStatement pst = connection.prepareStatement(TWORKORDERS_FINDAMOUNT)) {
            for (String id : workOrderIDs) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        total += rs.getDouble("amount");
                    }
                }
            }
        }
        return total;
    }

    /*
     * returns vat percentage
     */
    private double vatPercentage(LocalDate d) {
        return LocalDate.parse("2012-07-01").isBefore(d) ? 21.0 : 18.0;

    }

    /*
     * Creates the invoice in the database; returns the id
     */
    private String createInvoice(long numberInvoice, LocalDate dateInvoice, double vat, double total)
            throws SQLException {
        Connection connection = Jdbc.getCurrentConnection();

        String idInvoice = UUID.randomUUID().toString();
        try (PreparedStatement pst = connection.prepareStatement(TINVOICES_ADD)) {
            pst.setString(1, idInvoice);
            pst.setLong(2, numberInvoice);
            pst.setDate(3, java.sql.Date.valueOf(dateInvoice));
            pst.setDouble(4, vat);
            pst.setDouble(5, total);
            pst.setString(6, "NOT_YET_PAID");
            pst.setLong(7, 1L);
            pst.executeUpdate();
        }
        return idInvoice;
    }

    /*
     * Set the invoice number field in work order table to the invoice number
     * generated
     */
    private void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIDs) throws SQLException {
        Connection connection = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = connection.prepareStatement(TWORKORDERS_UPDATEINVOICEID)) {
            for (String id : workOrderIDs) {
                pst.setString(1, invoiceId);
                pst.setString(2, id);
                pst.executeUpdate();
            }
        }
    }

    /*
     * Sets status to INVOICED for every workorder
     */
    private void markWorkOrderAsInvoiced(List<String> ids) throws SQLException {
        Connection connection = Jdbc.getCurrentConnection();

        try (PreparedStatement pst = connection.prepareStatement(TWORKORDERS_UPDATESTATE)) {
            for (String id : ids) {
                pst.setString(1, id);
                pst.executeUpdate();
            }
        }
    }
}
