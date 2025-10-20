package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class WorkOrder {
	public enum WorkOrderState {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED
	}

	// natural attributes
	private LocalDateTime date;
	private String description;
	private double amount = 0.0;
	private WorkOrderState state = WorkOrderState.OPEN;

	// accidental attributes
	private Vehicle vehicle;
	private Mechanic mechanic;
	private Invoice invoice;
	private Set<Intervention> interventions = new HashSet<>();

	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {

	}

	/**
	 * Given the right conditions unlinks the workorder and the mechanic, 
	 * changes the state to FINISHED and computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 */
	public void markAsFinished() {

	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 */
	public void markBackToFinished() {

	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its state
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN state, or
	 */
	public void assignTo(Mechanic mechanic) {

	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its state back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state
	 */
	public void unassign() {

	}

	/**
	 * In order to assign a work order to another mechanic it first have to
	 * be moved back to OPEN state.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED state
	 */
	public void reopen() {

	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
