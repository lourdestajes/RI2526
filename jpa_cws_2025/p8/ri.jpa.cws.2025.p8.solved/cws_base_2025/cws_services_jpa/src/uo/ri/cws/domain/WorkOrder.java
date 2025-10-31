package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name = "TWORKORDERS", uniqueConstraints = {
		@UniqueConstraint(columnNames= {"date", "vehicle_id"})
})
public class WorkOrder extends BaseEntity {
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
	@ManyToOne private Vehicle vehicle;
	@ManyToOne private Mechanic mechanic;
	@ManyToOne private Invoice invoice;
	@OneToMany(mappedBy="workOrder") private Set<Intervention> interventions = new HashSet<>();

	WorkOrder() {
		// for JPA
	}
	
	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle, LocalDateTime.now(), description);
	}

	public WorkOrder(Vehicle v) {
		this(v, LocalDateTime.now(), "no-description");
	}

	public WorkOrder(Vehicle v, LocalDateTime t) {
		this(v, t, "no-description");
	}

	public WorkOrder(Vehicle v, LocalDateTime t, String desc) {
		ArgumentChecks.isNotNull( v, "The vehicle cannot be null" );
		ArgumentChecks.isNotBlank( desc, "The description cannot be null" );
		ArgumentChecks.isNotNull( t, "The time cannot be null" );
		this.date = t.truncatedTo( ChronoUnit.MILLIS );
		this.description = desc;
		Associations.Fixes.link( v, this );
	}

	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		StateChecks.isTrue(isFinished(), "The workorder is not FINISHED");
		StateChecks.isTrue(this.invoice != null, "The workorder is not linked with an invoiced");
		this.state = WorkOrderState.INVOICED;
	}

	/**
	 * Given the right conditions unlinks the workorder and the mechanic. 
	 * Changes the state to FINISHED and computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 */
	public void markAsFinished() {
		StateChecks.isTrue( this.state == WorkOrderState.ASSIGNED,
				"The work order is not in ASSIGNED state" );
		this.state = WorkOrderState.FINISHED;
		Associations.Assigns.unlink(mechanic, this);
		amount = computeAmount();
	}

	private double computeAmount() {
		double money = 0.0;
		for (Intervention i : interventions) {
			money += i.getAmount();
		}
		return money;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 */
	public void markBackToFinished() {
		StateChecks.isTrue(state == WorkOrderState.INVOICED);
		state = WorkOrderState.FINISHED;
	}

	
	/**
	 * Links (assigns) the work order to a mechanic and then changes its state
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN state, or
	 */
	public void assignTo(Mechanic mechanic) {
		ArgumentChecks.isNotNull(mechanic, "mechani cannot be null");
		StateChecks.isTrue(isOpen());
		StateChecks.isTrue(this.mechanic == null);
		Associations.Assigns.link(mechanic, this);
		this.state = WorkOrderState.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its state back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state
	 */
	public void unassign() {
		StateChecks.isTrue(isAssigned(), "The workorder is not ASSIGNED");
		Associations.Assigns.unlink(mechanic, this);
		state = WorkOrderState.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED state
	 */
	public void reopen() {
		StateChecks.isTrue(isFinished(), "The workorder is not FINISHED");
		this.state = WorkOrderState.OPEN;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderState getState() {
		return state;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
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

	public Vehicle getVehicle() {
		return this.vehicle;
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description + ", amount=" + amount + ", state=" + state
				+ ", vehicle=" + vehicle + "]";
	}

	public boolean isFinished() {

		return this.state == WorkOrderState.FINISHED;
	}

	public boolean isAssigned() {
		return this.state == WorkOrderState.ASSIGNED;
	}

	public boolean isOpen() {
		return this.state == WorkOrderState.OPEN;

	}

	public boolean isInvoiced() {
		return this.state == WorkOrderState.INVOICED;
	}

	
}
