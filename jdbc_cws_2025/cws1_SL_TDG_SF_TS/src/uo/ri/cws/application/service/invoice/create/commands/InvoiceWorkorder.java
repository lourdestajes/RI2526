package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoiceAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.math.Rounds;

public class InvoiceWorkorder {

	private List<String> ids;
	private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
	private InvoiceGateway ig = Factories.persistence.forInvoice();

	public InvoiceWorkorder(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds, "The list of work order ids cannot be null");
		ArgumentChecks.isFalse(workOrderIds.isEmpty(), "The list of work order ids cannot be empty");
		checkIds(workOrderIds);
		this.ids = workOrderIds;
	}

	private void checkIds(List<String> workOrderIds) {
		for (String id : workOrderIds) {
			ArgumentChecks.isNotBlank(id, "Work order id cannot be null or empty");
		}

	}

	public InvoiceDto execute() throws BusinessException {
		InvoiceDto result = null;
		try (Connection c = Jdbc.createThreadConnection()) {
			try {
				c.setAutoCommit(false);
				List<WorkorderRecord> workorders = checkWorkOrdersExist(ids);
				checkWorkOrdersFinished(workorders);
				long numberInvoice = ig.findNextInvoiceNumber();
				double amount = calculateTotalInvoice(workorders); // vat not included
				InvoiceRecord invoice = createInvoice(numberInvoice, amount);
				updateWorkordersToInvoice(workorders, invoice.id);
				result = InvoiceAssembler.toDto(invoice);
				c.commit();
			} catch (Exception e) {
				c.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private void updateWorkordersToInvoice(List<WorkorderRecord> workorders, String invoiceId) {
		for (WorkorderRecord wr : workorders) {
			wr.invoiceId = invoiceId;
			wr.state = "INVOICED";
			wog.update(wr);
		}
	}

	/*
	 * checks whether every work order exist
	 */
	private List<WorkorderRecord> checkWorkOrdersExist(List<String> workOrderIDS) throws BusinessException {
		List<WorkorderRecord> workorders = new ArrayList<>();
		for (String id : workOrderIDS) {
			workorders.add(wog.findById(id).orElseThrow(() -> new BusinessException("Workorder does not exist")));
		}
		return workorders;
	}

	/*
	 * checks whether every work order id is FINISHED
	 */
	private void checkWorkOrdersFinished(List<WorkorderRecord> workorders) throws BusinessException {

		for (WorkorderRecord wr : workorders) {
			BusinessChecks.isTrue("FINISHED".equals(wr.state));
		}
	}

	/*
	 * Compute total amount of the invoice (as the total of individual work orders'
	 * amount
	 */
	private double calculateTotalInvoice(List<WorkorderRecord> workorders) {
		double total = 0.0;
		for (WorkorderRecord wr : workorders) {
			total += wr.amount;
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
	 * Creates the invoice in the database; returns the record
	 */
	private InvoiceRecord createInvoice(long numberInvoice, double total) {

		InvoiceRecord invoice = new InvoiceRecord();
		invoice.id = UUID.randomUUID().toString();
		invoice.number = numberInvoice;
		invoice.date = LocalDate.now();
		invoice.vat = (total * (vatPercentage(invoice.date) / 100));
		invoice.amount = Rounds.toCents(total + invoice.vat);
		invoice.status = "NOT_YET_PAID";
		invoice.version = 1L;
		ig.add(invoice);

		return invoice;
	}

}
