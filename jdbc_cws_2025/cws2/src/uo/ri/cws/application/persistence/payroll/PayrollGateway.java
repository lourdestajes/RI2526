package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public interface PayrollGateway extends Gateway<PayrollRecord> {

	public class PayrollRecord extends BaseRecord {
		public String contract_id;	
		public LocalDate date;		
		public double baseSalary;  // monthly base salary
		public double extraSalary; // if applies
		public double productivityEarning;
		public double trienniumEarning;
		public double taxDeduction; // descuento del IRPF; tax deduction (money)
		public double nicDeduction; // Aportación a la seguridad social; National
	                        	  	 // Insurance Contribution (money)
	}
	
	List<PayrollRecord> findByContractId(String contractId);

	List<PayrollRecord> findLastMonthPayrolls();

	Optional<PayrollRecord> findLastMonthPayrollForContract(String contractId);

	Optional<PayrollRecord> findByContractIdAndDate(String id, LocalDate date);
	
}
