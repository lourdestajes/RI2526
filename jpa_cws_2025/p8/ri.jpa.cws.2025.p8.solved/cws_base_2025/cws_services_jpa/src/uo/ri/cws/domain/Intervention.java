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
@Entity
@Table(name = "TINTERVENTIONS",
	uniqueConstraints = {
			@UniqueConstraint(columnNames= {"workOrder_id", "mechanic_id", "date"})
	}
)
public class Intervention extends BaseEntity {
	@ManyToOne private WorkOrder workOrder;
	@ManyToOne private Mechanic mechanic;

	private LocalDateTime date;
	private int minutes;

	@OneToMany(mappedBy="intervention")
	private Set<Substitution> substitutions = new HashSet<>();

	Intervention() { }

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(mechanic, workOrder, LocalDateTime.now(), minutes);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder,
			LocalDateTime date, int minutes) {
		ArgumentChecks.isNotNull( mechanic );
		ArgumentChecks.isNotNull( workOrder );
		ArgumentChecks.isNotNull( date );
		ArgumentChecks.isTrue( minutes >= 0 );

		this.date = date.truncatedTo( ChronoUnit.MILLIS );
		this.minutes = minutes;
		Associations.Intervenes.link(workOrder, this, mechanic);
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		ArgumentChecks.isTrue( minutes > 0 );
		this.minutes = minutes;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	@Override
	public String toString() {
		return "Intervention ["
				+ "workOrder=" + workOrder
				+ ", mechanic=" + mechanic
				+ ", date=" + date
				+ ", minutes=" + minutes
				+ "]";
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSustitutions() {
		return substitutions;
	}

	public double getAmount() {
		return getWorkAmount() + getSparesAmount();
	}

	private double getWorkAmount() {
		double pricePerHour = getWorkOrder().getVehicle().getVehicleType().getPricePerHour();
		return minutes * pricePerHour / 60.0;
	}

	private double getSparesAmount() {
		double total = 0.0;
		for(Substitution s: substitutions) {
			total += s.getAmount();
		}
		return total;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Set<Substitution> _getSubstitutions() {
		return this.substitutions;
	}

}
