package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.date.Dates;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Rounds;

public class GeneratePayroll implements Command<List<PayrollDto>> {

	private static final int PAYS_PER_YEAR = 14;
    private static final double SOCIAL_SEC_PERCENT = 0.05;
    private static final int MONTHS_PER_YEAR = 12;
    
	private ContractGateway cRepo = Factories.persistence.forContract();
	private PayrollGateway pRepo = Factories.persistence.forPayroll();
	private ProfessionalGroupGateway pgRepo = Factories.persistence.forProfessionalGroup();
	private InterventionGateway interventionRepo = Factories.persistence.forIntervention();
	private WorkOrderGateway woRepo = Factories.persistence.forWorkOrder();
	private DtoAssembler assembler = new DtoAssembler();

	private LocalDate date;

	public GeneratePayroll(LocalDate date) {
		ArgumentChecks.isNotNull(date, "The date can't be null");
		
		// Set to last day of the previous month
		this.date = Dates.lastDayOfMonth(date.minusMonths(1));
	}

	public GeneratePayroll() {
		this( LocalDate.now() );
	}

	 
	public List<PayrollDto> execute() throws BusinessException {
		List<ContractRecord> contracts = cRepo.findAllInForceThisMonth(date.minusMonths(1));

		List<PayrollRecord> payrolls = new ArrayList<>();
		for (ContractRecord c: contracts) {
			if ( payrollAlreadyGenerated(c.id, date) ) {
				continue;
			}
			PayrollRecord p = createPayroll(c, date);
			payrolls.add(p);
			
			pRepo.add(p);
		}

		return assembler.toDtoList( payrolls );
	}

	private boolean payrollAlreadyGenerated(String id, LocalDate date) {
		return pRepo.findByContractIdAndDate(id, date).isPresent();
	}

	private PayrollRecord createPayroll(ContractRecord c, LocalDate minusMonths) {
		PayrollRecord result = new PayrollRecord();
        result.date = date;
        result.contract_id = c.id;

        // computations
        fillEarnings(c, result);
        fillDeductions(c, result);
		return result;
	}

	private void fillEarnings(ContractRecord contract, PayrollRecord payroll) {
        payroll.baseSalary = Rounds.toMilis( contract.annualBaseSalary / PAYS_PER_YEAR );
        payroll.extraSalary = isBonusMonth() ? payroll.baseSalary : 0.0;
        payroll.productivityEarning = calculateProductivityBonus(contract);
        payroll.trienniumEarning = calculateTrienniumPayment(contract);
    }

    private double calculateTrienniumPayment(ContractRecord contract) {
        long years = yearsBetweenDates(contract.startDate, date);
        double trienniumPay = getTrienniumPayment(contract.professionalGroupId);
        return Rounds.toMilis( years / 3 * trienniumPay );
    }

    private double getTrienniumPayment(String professionalGroupId) {
    	return pgRepo.findById(professionalGroupId).get().trienniumPayment;
	}

	private long yearsBetweenDates(LocalDate d1, LocalDate d2) {
        return ChronoUnit.DAYS.between(d1, d2) / 365;
    }

    private double calculateProductivityBonus(ContractRecord contract) {
        double percent = getProductivityRate(contract.professionalGroupId);

        List<WorkOrderRecord> wos = findInvoicedByMechanic(contract.mechanicId);
        double totalInvoiced = wos.stream()
                .mapToDouble(wo -> wo.amount)
                .sum();

        return Rounds.toMilis( percent * totalInvoiced );
    }

    private double getProductivityRate(String pgId) {
    	return pgRepo.findById(pgId).get().productivityRate;
	}

	private List<WorkOrderRecord> findInvoicedByMechanic(String mechanicId) {
		List<InterventionRecord> interventions = interventionRepo.findByMechanicId(mechanicId);
        return interventions.stream()
                .map(i -> woRepo.findById(i.workorderId).get())
                .filter(wo -> "INVOICED".equals(wo.state))
                .filter(wo -> doneTheMonthOfThePayroll( wo.date ))
                .toList();
    }
    
    private boolean doneTheMonthOfThePayroll(LocalDateTime woDate) {
        return date.getMonth().equals(woDate.getMonth())
        		&& date.getYear() == woDate.getYear();
    }
    
    private boolean isBonusMonth() {
        Month month = date.getMonth();
        return month.equals(Month.JUNE) || month.equals(Month.DECEMBER);
    }

    private void fillDeductions(ContractRecord c, PayrollRecord payroll) {
        payroll.taxDeduction = computeTaxAmount(c, payroll);
        payroll.nicDeduction = computeSocialSecurityAmount(c);
    }

    private double computeSocialSecurityAmount(ContractRecord contract) {
        return Rounds.toMilis( 
        		contract.annualBaseSalary / MONTHS_PER_YEAR
                * SOCIAL_SEC_PERCENT 
              );
    }

    private double computeTaxAmount(ContractRecord c, PayrollRecord payroll) {
        double taxRate = c.taxRate;
        return Rounds.toMilis( taxRate * computeGrossSalary(payroll) );
    }

    private double computeGrossSalary(PayrollRecord payroll) {
        return payroll.baseSalary + payroll.extraSalary + payroll.productivityEarning + payroll.trienniumEarning;
    }

    
}
