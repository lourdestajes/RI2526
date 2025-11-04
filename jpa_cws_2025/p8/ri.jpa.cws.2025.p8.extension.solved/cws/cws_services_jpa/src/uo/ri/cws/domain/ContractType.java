package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TCONTRACTTYPES")
public class ContractType extends BaseEntity {
	
	// Atributos naturales
	@Column(unique=true) private String name;
	private double compensationDaysPerYear;
	
	// Atributos accidentales
	@OneToMany(mappedBy="contractType") private Set<Contract> contracts = new HashSet<>();
	
	ContractType() {
		// for JPA
	}
	
	public ContractType(String name, double days) {
		ArgumentChecks.isNotBlank( name, "Name cannot be null or empty" );
		ArgumentChecks.isTrue( days >= 0, "Compensation days per year must be non-negative" );
		this.name = name;
		this.compensationDaysPerYear = days;
	}


	public String getName() {
		return name;
	}


	public double getCompensationDaysPerYear() {
		return compensationDaysPerYear;
	}


	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	Set<Contract> _getContracts() {
		return contracts;
	}

	@Override
	public String toString() {
		return "ContractType [name=" + name + ", compensationDaysPerYear=" + compensationDaysPerYear + "]";
	}


	

}
