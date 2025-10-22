package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class Invoice {
	public enum InvoiceState { NOT_YET_PAID, PAID }

	// natural attributes
	private Long number;
	private LocalDate date;
	private double amount;
	private double vat;
	private InvoiceState state = InvoiceState.NOT_YET_PAID;

	// accidental attributes
	private Set<WorkOrder> workOrders = new HashSet<>();
	private Set<Charge> charges = new HashSet<>();

	public Invoice(Long number) {
		// call full constructor with sensible defaults
		this(number, LocalDate.now(), List.of());
	}

	public Invoice(Long number, LocalDate date) {
		// call full constructor with sensible defaults
		this(number, date, List.of());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		// check arguments (always), through IllegalArgumentException
		ArgumentChecks.isNotNull( number, "The invoice number cannot be null" );
		ArgumentChecks.isNotNull( date, "The invoice date cannot be null" );
		ArgumentChecks.isNotNull( workOrders, "The work orders list cannot be null" );
		ArgumentChecks.isTrue( number >= 0, "The invoice number cannot be negative" );
		// store the number
		this.number = number;
		// store the date
		this.date = date;
		// add every work order calling addWorkOrder( w )
		for (WorkOrder w : workOrders) {
			this.addWorkOrder(w);
		}
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		double total = 0.0;
		for (WorkOrder w : workOrders) {
			total += w.getAmount();
		}
		if (date.isBefore(LocalDate.of(2012, 7, 1))) {
			this.vat = total * 0.18;
		} else {
			this.vat = total * 0.21;
		}
		this.amount = total + vat;
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 * @throws IllegalStateException if the workorder status is not FINISHED
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		ArgumentChecks.isNotNull(workOrder, "The work order cannot be null");
		StateChecks.isTrue(isNotSettled(), "Cannot add work orders to a settled invoice");
		StateChecks.isTrue(workOrder.isFinished(), "The work order must be finished to be invoiced");
		Associations.Bills.link(this, workOrder);
		computeAmount();
		workOrder.markAsInvoiced();
	}

	/**
	 * Removes a work order from the invoice, updates the workorder state
	 * and recomputes amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 * @throws IllegalArgumentException if the invoice does not contain the workorder
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		ArgumentChecks.isNotNull(workOrder, "Workorder cannot be null");
		ArgumentChecks.isTrue(this.workOrders.contains(workOrder));
		StateChecks.isTrue(isNotSettled());
		Associations.Bills.unlink(this, workOrder);
		workOrder.markBackToFinished();
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * @throws IllegalStateException if
	 * 	- Is already settled
	 *  - Or the amounts paid with charges to payment means do not cover
	 *  	the total of the invoice
	 */
	public void settle() {
		StateChecks.isTrue(isNotSettled());
		StateChecks.isTrue( charges.stream().mapToDouble(ch -> ch.getAmount()).sum() == this.amount);
		state = InvoiceState.PAID;
	}

	
	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount=" + amount + ", vat=" + vat + ", state="
				+ state + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		return Objects.equals(number, other.number);
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceState getState() {
		return state;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>( workOrders );
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public boolean isNotSettled() {
		return this.state == InvoiceState.NOT_YET_PAID;
	}

	public boolean isSettled() {
		return this.state != InvoiceState.NOT_YET_PAID;

	}

}
