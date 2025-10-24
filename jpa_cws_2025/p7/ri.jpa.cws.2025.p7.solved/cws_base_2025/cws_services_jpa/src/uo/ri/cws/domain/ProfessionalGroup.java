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
@Table(name="TPROFESSIONALGROUPS")
public class ProfessionalGroup extends BaseEntity {

	@Column(unique=true) private String name;
	private double trienniumSalary;
	private double productivityPlus;
	
	// Atributos accidentales
	@OneToMany(mappedBy="professionalGroup") private Set<Contract> contracts = new HashSet<>();

	ProfessionalGroup() {
		// for JPA
	}
	
	public ProfessionalGroup(String name, double trienniumSalary, double productivityPlus) {
		ArgumentChecks.isNotBlank( name, "Name cannot be null or empty" );
		ArgumentChecks.isTrue( trienniumSalary >= 0, "triennium Salary must be non-negative" );
		ArgumentChecks.isTrue( productivityPlus >= 0, "Productivity plus must be non-negative" );
		this.name = name;
		this.trienniumSalary = trienniumSalary;
		this.productivityPlus = productivityPlus;
	}

	public String getName() {
		return name;
	}

	public double getTrienniumSalary() {
		return trienniumSalary;
	}

	public double getProductivityRate() {
		return productivityPlus;
	}

	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	Set<Contract> _getContracts() {
		return contracts;
	}

	@Override
	public String toString() {
		return "ProfessionalGroup [name=" + name + ", trienniumSalary=" + trienniumSalary + ", productivityPlus="
				+ productivityPlus + "]";
	}
	
}
