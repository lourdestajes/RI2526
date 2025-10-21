package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;

public class Payroll {

	private static final double NIC_RATE = 0.05;
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
		ArgumentChecks.isTrue(!payrollDate.isBefore(c.getStartDate()),
				"Payroll date cannot be before contract start date");
		this.date = payrollDate.with(TemporalAdjusters.lastDayOfMonth());
		Associations.Generates.link(c, this);
		fillPayrollDetails();
	}

	private void fillPayrollDetails() {
		fillEarnings();
		fillDeductions();
	}

	private void fillDeductions() {
		double grossSalary = getGrossSalary();
		this.taxDeduction = grossSalary * contract.getTaxRate();
		this.nicDeduction = contract.getAnnualBaseSalary() / 12 * NIC_RATE;
	}

	private void fillEarnings() {
		this.baseSalary = contract.getAnnualBaseSalary() / 14;
		this.trienniumEarning = calculateTrienniumEarning();
		this.productivityEarning = calculateProductivityEarning();
		this.extraSalary = (date.getMonthValue() == 6 || date.getMonthValue() == 12) ? baseSalary: 0.0;
	}

	private double calculateTrienniumEarning() {
		return (calculateFullYearsInService() /3) * contract.getProfessionalGroup().getTrienniumSalary();
	}

	private int calculateFullYearsInService() {
		return (int) (ChronoUnit.DAYS.between(contract.getStartDate(), date) / 365);
	}
	
	private double calculateProductivityEarning() {
		double productivityBonus = contract.getProfessionalGroup().getProductivityRate();
		List<WorkOrder> productivity = findInvoicedWorkOrders(); 
		double totalInvoiced = productivity.stream().mapToDouble(wo -> wo.getAmount()).sum();
		return totalInvoiced * productivityBonus;
	}

	private List<WorkOrder> findInvoicedWorkOrders() {
		return contract.getMechanic().getInterventions().stream()
				.map(i-> i.getWorkOrder())
				.filter(WorkOrder::isInvoiced)
				.filter(wo -> wo.getDate().getYear() == date.getYear()
				&& wo.getDate().getMonthValue() == date.getMonthValue())
				.toList();
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

	public double getGrossSalary() {
		return baseSalary + trienniumEarning + productivityEarning + extraSalary;
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
