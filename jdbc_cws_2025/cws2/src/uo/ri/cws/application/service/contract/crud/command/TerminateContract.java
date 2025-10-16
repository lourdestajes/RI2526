package uo.ri.cws.application.service.contract.crud.command;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class TerminateContract implements Command<Void> {

	private static final int DAYS_PER_YEAR = 365;
	private String id = null;
	private ContractGateway contractR = Factories.persistence.forContract();
	private ContractTypeGateway contractTypeG = Factories.persistence.forContractType();
	private PayrollGateway payrollG = Factories.persistence.forPayroll();

	public TerminateContract(String arg) {
		ArgumentChecks.isNotNull(arg, "Contract id cannot be null");
		ArgumentChecks.isNotBlank(arg, "Contract id cannot be empty");
		this.id = arg;
	}

	 
	public Void execute() throws BusinessException {

		Optional<ContractRecord> oc = contractR.findById(id);
		BusinessChecks.exists( oc, "Contract does not exist");

		ContractRecord c = oc.get();
		BusinessChecks.isTrue( "IN_FORCE".equals(c.state),
                "The contract must be in-force to be terminated");

		terminate(c, LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

		return null;
	}

	   public void terminate(ContractRecord c, LocalDate endDate) {
		ArgumentChecks.isNotNull(endDate, "Termination date cannot be null");
		ArgumentChecks.isTrue(endDate.isAfter(c.startDate), 
				"Termination must be after the start date");
		
        c.endDate = endDate.with(TemporalAdjusters.lastDayOfMonth());
        c.settlement = calculateSettlement(c);

        c.state = "TERMINATED";
        contractR.update(c);
    }

    private double calculateSettlement(ContractRecord c) {
        long completeYearsWorked = computeCompleteYearsInForce(c.startDate, c.endDate);
        if (completeYearsWorked < 1) {
        	return 0.0;
        }

        double dailyGrossSalary = avgDailyGrossSalary(c.id);
        double compensationDays = contractTypeG.findById(c.contractTypeId).get().compensationDaysPerYear;
        return completeYearsWorked * dailyGrossSalary * compensationDays;
    }

    
    private double avgDailyGrossSalary(String id) {
        double monthlyEarnings = lastTwelvePayrolls(id).stream()
                .mapToDouble(pr -> grossSalary(pr))
                .sum();

        return monthlyEarnings / DAYS_PER_YEAR;
    }

    private double grossSalary(PayrollRecord pr) {
		return pr.baseSalary + pr.extraSalary + pr.productivityEarning + pr.trienniumEarning;
	}

	private List<PayrollRecord> lastTwelvePayrolls(String id) {
		return getPayrolls(id).stream().sorted((a, b) -> b.date.compareTo(a.date)).limit(12).toList();
	}

	private List<PayrollRecord> getPayrolls(String id) {
		return payrollG.findByContractId(id);
	}

	public long computeCompleteYearsInForce(LocalDate startDate, LocalDate endDate) {
		long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
		return days / DAYS_PER_YEAR;
	}

}
