package uo.ri.cws.application.service.invoice.create.commands;

import java.time.LocalDate;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

public class CreateInvoiceFor implements Command<InvoiceDto>{

	private DtoAssembler assembler = new DtoAssembler();
	private List<String> workorderIds;
	private WorkOrderGateway woGateway = Factories.persistence.forWorkOrder();
	private InvoiceGateway invoiceGateway = Factories.persistence.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		validateArguments(workOrderIds);
		this.workorderIds = workOrderIds;
	}

	private void validateArguments(List<String> ids) {
		ArgumentChecks.isNotNull( ids );
		ArgumentChecks.isFalse( ids.isEmpty() );
		ArgumentChecks.isFalse(ids.stream().anyMatch(i -> i == null));

		for (String id : ids) {
			ArgumentChecks.isNotBlank(id, "workorder id cannot be null nor blank");
		}
	}

	 
	public InvoiceDto execute() throws BusinessException {
		List<WorkOrderRecord> workOrderRecords = getAllWorkOrders(workorderIds);
		assertAllWorkOrdersExist(workorderIds, workOrderRecords);
		assertAllWorkOrdersFinished(workOrderRecords);

		long numberInvoice = generateInvoiceNumber();
		LocalDate date = LocalDate.now();
		double amount = calculateTotal(workOrderRecords); // vat not included
		double vat = amount * vatFactor( date );
		amount += vat; // now vat included
		amount = Rounds.toCents( amount );
		vat = Rounds.toCents(vat);

		InvoiceRecord invoice = createInvoice(numberInvoice, date, vat, amount);
		linkWorkordersToInvoice(invoice.id, workOrderRecords);
		markWorkOrderAsInvoiced(workOrderRecords);
		updateWorkOrders(workOrderRecords);

		return assembler.toDto(invoice);//DtoAssembler.toDto(invoice);
	}

	private List<WorkOrderRecord> getAllWorkOrders(List<String> arg) {
		return woGateway.findByIds(arg);
	}

	/**
	 * checks whether every work order exist
	 */
	private void assertAllWorkOrdersExist(List<String> workOrderIDS,
			List<WorkOrderRecord> records) throws BusinessException {
		
		BusinessChecks.isTrue(records.size() == workOrderIDS.size(),
				"Some workorder does not exist");
	}

	/**
	 * checks whether every work order id is FINISHED
	 */
	private void assertAllWorkOrdersFinished(List<WorkOrderRecord> records)
			throws BusinessException {
		
		BusinessChecks.isTrue(
				records.stream().allMatch(record -> isFinisihed(record)),
				"Some workorder is not finished");
	}

	private boolean isFinisihed(WorkOrderRecord record) {
		return record.state.equalsIgnoreCase("FINISHED");
	}

	/**
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	private long generateInvoiceNumber() {
		return invoiceGateway.getNextInvoiceNumber();
	}

	/**
	 * Compute total amount of the invoice (as the total of individual work
	 * orders' amount
	 */
	private double calculateTotal(List<WorkOrderRecord> records)
			throws BusinessException {

		double total = 0.0;
		for (WorkOrderRecord record : records) {
			total += record.amount;
		}
		return total;
	}

	/**
	 * returns vat factor
	 */
	private double vatFactor(LocalDate date) {
		LocalDate refVatDate = LocalDate.parse("2012-07-01");
		return refVatDate.isBefore(date) 
				? 0.21
				: 0.18;
	}

	/**
	 * Creates the invoice in the database; returns the id
	 */
	private InvoiceRecord createInvoice(long numberInvoice,
			LocalDate dateInvoice, double vat, double amount) {

		InvoiceRecord record = new InvoiceRecord();
		record.number = numberInvoice;
		record.date = dateInvoice;
		record.vat = vat;
		record.amount = amount;
		record.state = "NOT_YET_PAID";
		record.version = 1L;
		invoiceGateway.add(record);
		return record;
	}

	/**
	 * Set the invoice number field in work order table to the invoice number
	 * generated
	 */
	private void linkWorkordersToInvoice(String invoiceId,
			List<WorkOrderRecord> records) {
		for (WorkOrderRecord record : records) {
			record.invoiceId = invoiceId;
		}
	}

	/**
	 * Sets state to INVOICED for every workorder
	 */
	private void markWorkOrderAsInvoiced(List<WorkOrderRecord> records) {
		for (WorkOrderRecord record : records) {
			record.state = "INVOICED";
		}
	}

	/**
	 * Updates work orders in the database
	 */
	private void updateWorkOrders(List<WorkOrderRecord> records) {
		for (WorkOrderRecord record : records) {
			woGateway.update(record);
		}
	}

}

