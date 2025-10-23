package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TMechanics")
public class Mechanic extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String nif;
	private String surname;
	private String name;

	// accidental attributes
	@OneToMany(mappedBy="mechanic")
	private Set<WorkOrder> assigned = new HashSet<>();
	@Transient 
	private Set<Intervention> interventions = new HashSet<>();
	@Transient
	private Set<Contract> contracts = new HashSet<>();
	
	Mechanic() {
		// for JPA
	}
	
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
	public String toString() {
		return "Mechanic [nif=" + nif + ", surname=" + surname + ", name=" + name + "]";
	}

	public Optional<Contract> getContractInForce() {
		Optional<Contract> optional = contracts.stream().filter(c -> c.isInForce()).findFirst();
		return optional;
	}
}
