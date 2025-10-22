package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Mechanic {
	// natural attributes
	private String nif;
	private String surname;
	private String name;

	// accidental attributes
	private Set<WorkOrder> assigned = new HashSet<>();
	private Set<Intervention> interventions = new HashSet<>();
	private Set<Contract> contracts = new HashSet<>();
	
	public Mechanic(String nif, String surname, String name) {
		// check arguments (always), through IllegalArgumentException
		ArgumentChecks.isNotBlank( nif, "NIF cannot be null or empty" );
		ArgumentChecks.isNotBlank( surname, "Surname cannot be null or empty" );
		ArgumentChecks.isNotBlank( name, "Name cannot be null or empty" );
		
		// store the attributes
		this.nif = nif;
		this.surname = surname;
		this.name = name;
	}
	
	public String getNif() {
		return nif;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>( assigned );
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	Set<Contract> _getContracts() {
		return contracts;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nif);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mechanic other = (Mechanic) obj;
		return Objects.equals(nif, other.nif);
	}

	@Override
	public String toString() {
		return "Mechanic [nif=" + nif + ", surname=" + surname + ", name=" + name + "]";
	}

	public Optional<Contract> getContractInForce() {
		Optional<Contract> optional = contracts.stream().filter(c -> c.isInForce()).findFirst();
		return optional;
	}
}
