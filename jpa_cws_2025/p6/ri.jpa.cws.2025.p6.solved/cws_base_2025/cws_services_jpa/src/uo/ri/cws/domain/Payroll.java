package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;

public class Payroll {

	// Atributos naturales
	private LocalDate date;
	private double baseSalary;
	private double trienniumEarning;
	private double productivityEarning;
	private double extraSalary; 
	private double taxDeduction;
	private double nicDeduction;
	
	// Atributos accidentales
	private Contract contract;
	
	public Payroll(Contract c, LocalDate payrollDate) {
		ArgumentChecks.isNotNull(c, "Contract cannot be null");
		ArgumentChecks.isNotNull(payrollDate, "Payroll date cannot be null");
		this.date = payrollDate;
		Associations.Generates.link(c, this);
	}

	public LocalDate getDate() {
		return date;
	}

	public double getMonthlyBaseSalary() {
		return baseSalary;
	}

	public double getTrienniumEarning() {
		return trienniumEarning;
	}

	public double getProductivityEarning() {
		return productivityEarning;
	}

	public double getTaxDeduction() {
		return taxDeduction;
	}

	public double getNicDeduction() {
		return nicDeduction;
	}

	public double getExtraSalary() {
		return extraSalary;
	}

	public Contract getContract() {
		return contract;
	}

	void _setContract(Contract c) {
		this.contract = c;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(contract, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payroll other = (Payroll) obj;
		return Objects.equals(contract, other.contract) && Objects.equals(date, other.date);
	}

	@Override
	public String toString() {
		return "Payroll [date=" + date + ", baseSalary=" + baseSalary + ", trienniumEarning=" + trienniumEarning
				+ ", productivityEarning=" + productivityEarning + ", extraSalary=" + extraSalary + ", contract="
				+ contract + "]";
	}


	
}
