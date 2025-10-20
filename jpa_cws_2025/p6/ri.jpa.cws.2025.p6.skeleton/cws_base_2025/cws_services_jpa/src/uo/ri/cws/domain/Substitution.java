package uo.ri.cws.domain;

public class Substitution {
	// natural attributes
	private int quantity;

	// accidental attributes
	private SparePart sparePart;
	private Intervention intervention;



	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

}
