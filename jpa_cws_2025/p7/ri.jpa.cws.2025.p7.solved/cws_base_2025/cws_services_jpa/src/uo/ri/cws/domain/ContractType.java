package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class ContractType {
	
	// Atributos naturales
	private String name;
	private double compensationDaysPerYear;
	
	// Atributos accidentales
	private Set<Contract> contracts = new HashSet<>();
	
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
	public int hashCode() {
		return Objects.hash(name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContractType other = (ContractType) obj;
		return Objects.equals(name, other.name);
	}


	@Override
	public String toString() {
		return "ContractType [name=" + name + ", compensationDaysPerYear=" + compensationDaysPerYear + "]";
	}


	

}
