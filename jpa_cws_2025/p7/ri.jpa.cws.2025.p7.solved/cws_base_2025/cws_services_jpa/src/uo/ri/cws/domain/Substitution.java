package uo.ri.cws.domain;

import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;

public class Substitution {
	// natural attributes
	private int quantity;

	// accidental attributes
	private SparePart sparePart;
	private Intervention intervention;



	public Substitution(SparePart spare, Intervention intervention, int units) {
		ArgumentChecks.isNotNull(spare, "The spare part cannot be null");
		ArgumentChecks.isNotNull(intervention, "The intervention cannot be null");
		ArgumentChecks.isTrue(units > 0, "The quantity must be positive");
		this.quantity = units;
		Associations.Substitutes.link(spare, this, intervention);
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	@Override
	public int hashCode() {
		return Objects.hash(intervention, sparePart);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Substitution other = (Substitution) obj;
		return Objects.equals(intervention, other.intervention) && Objects.equals(sparePart, other.sparePart);
	}

	@Override
	public String toString() {
		return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart + ", intervention=" + intervention
				+ "]";
	}

	public double getAmount() {
		return quantity * sparePart.getPrice();
	}

	

}
