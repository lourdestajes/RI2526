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
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddContract implements Command<ContractDto> {
    private static final int DAYS_PER_YEAR = 365;

	private DtoAssembler assembler = new DtoAssembler();
	private ContractGateway contractRepo = Factories.persistence.forContract();
	private MechanicGateway mechanicRepo = Factories.persistence.forMechanic();
	private ProfessionalGroupGateway groupRepo = Factories.persistence.forProfessionalGroup();
	private ContractTypeGateway typeRepo = Factories.persistence.forContractType();
	private PayrollGateway payrollRepo = Factories.persistence.forPayroll();

	private ContractDto dto;

	public AddContract(ContractDto arg) {
		ArgumentChecks.isNotNull(arg, "Contract cannot be null");
		ArgumentChecks.isNotNull(arg.mechanic, "Mechanic cannot be null");
		ArgumentChecks.isNotNull(arg.contractType, "Type name cannot be null");
		ArgumentChecks.isNotNull(arg.professionalGroup, "Professional group name cannot be null");
		if ("FIXED_TERM".equals(arg.contractType.name)) {
			ArgumentChecks.isNotNull(arg.endDate);
		}

		ArgumentChecks.isNotBlank(arg.mechanic.nif, "Mechanic nif cannot be empty");
		ArgumentChecks.isNotBlank(arg.contractType.name, "Type name cannot be null");
		ArgumentChecks.isNotBlank(arg.professionalGroup.name, "Professional group name cannot be empty");
		ArgumentChecks.isTrue( arg.annualBaseSalary > 0, "Year base salary cannot be negative");
		this.dto = arg;
	}

	 
	public ContractDto execute() throws BusinessException {
		
		validateAndCompleteDto();
		terminatePreviousContractIfExists(dto.mechanic.id);
		
		dto.startDate = firstDayOfNextMonth();
		dto.endDate = computeEndDate();
		dto.taxRate = computeTaxRate(dto.annualBaseSalary);
		dto.state = "IN_FORCE";
		 // The settlement will be computed when the contract ends

		ContractRecord cr = assembler.toRecord(dto);
		contractRepo.add( cr );

		dto.id = cr.id;
		dto.version = cr.version;
		return dto;
	}

	private double computeTaxRate(double annualBaseSalary) {
		if (annualBaseSalary < 12450) {
			return 0.19;
		} else if (annualBaseSalary < 20200) {
			return 0.24;
		} else if (annualBaseSalary < 35200) {
			return 0.30;
		} else if (annualBaseSalary < 60000) {
			return 0.37;
		} else if (annualBaseSalary < 300000) {
			return 0.45;
		} else {
			return 0.47;
		}
	}

	private LocalDate computeEndDate() {
		return "FIXED_TERM".equals(dto.contractType.name) ? dto.endDate : null;
	}

	/*
	 * Check mechanic exists
	 * Check contract type exists
	 * Check professional group exists
	 * Check end date is later than start date
	 */
	private void validateAndCompleteDto() throws BusinessException {
		 // end date can be null (PERMANENT, SEASONAL)
		BusinessChecks.isTrue( 
				dto.endDate == null || dto.endDate.isAfter(dto.startDate), 
				"End date must be later than start date"
			);		

		Optional<ContractTypeRecord> optype = typeRepo.findByName( dto.contractType.name );
		BusinessChecks.exists( optype, "The contract type does not exist");
		ContractTypeRecord type = optype.get();
		dto.contractType.id = type.id;
		dto.contractType.compensationDaysPerYear = type.compensationDaysPerYear;
		
		Optional<ProfessionalGroupRecord> opprofGroup = groupRepo.findByName( dto.professionalGroup.name );
		BusinessChecks.exists( opprofGroup, "The professional group does not exist");
		ProfessionalGroupRecord pg = opprofGroup.get();
		dto.professionalGroup.id = pg.id;
		dto.professionalGroup.productivityRate = pg.productivityRate;
		dto.professionalGroup.trieniumPayment = pg.trienniumPayment;
		
		Optional<MechanicRecord> opmechanic = mechanicRepo.findByNif( dto.mechanic.nif );
		BusinessChecks.exists( opmechanic, "The mechanic does not exist");
		MechanicRecord mechanic = opmechanic.get();
		dto.mechanic.id = mechanic.id;
		dto.mechanic.name = mechanic.name;
		dto.mechanic.surname = mechanic.surname;
	}
	
	/**
	 * Marks previous as finished if there is one
	 * The in-force contract changes to a terminated state, with the 
	 * termination date being the last day of the current month.
	 * @param m
	 */
	private void terminatePreviousContractIfExists(String mechanicId) {
		Optional<ContractRecord> inForce = contractRepo.findInForceForMechanic(mechanicId);
		if (inForce.isEmpty()) {
			return;
		}
					
		ContractRecord c = inForce.get();
		c.endDate = lastDayOfCurrentMonth();
		c.settlement = calculateSettlement(c);
        c.state = "TERMINATED";
        
		contractRepo.update(c);
	}

	private LocalDate lastDayOfCurrentMonth() {
		return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
	}

	private LocalDate firstDayOfNextMonth() {
		return LocalDate.now()
				.plusMonths(1)
				.with(TemporalAdjusters.firstDayOfMonth());
	}

	private double calculateSettlement(ContractRecord c) {
		long completeYearsWorked = computeCompleteYearsInForce(c.startDate, c.endDate);
		if (completeYearsWorked < 1) {
			return 0.0;
		}

		ContractTypeRecord type = typeRepo.findById(c.contractTypeId).get();
		double compensationDays = type.compensationDaysPerYear;
		double dailyGrossSalary = avgDailyGrossSalary(c.id);
		
		return completeYearsWorked * dailyGrossSalary * compensationDays;
	}

	public long computeCompleteYearsInForce(LocalDate startDate, LocalDate endDate) {
		long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
		return days / DAYS_PER_YEAR;
	}
	    
	private double avgDailyGrossSalary(String id) {
		double monthlyEarnings = lastTwelvePayrolls(id).stream()
				.mapToDouble(pr -> pr.baseSalary)
				.sum();

		return monthlyEarnings / DAYS_PER_YEAR;
	}

	private List<PayrollRecord> lastTwelvePayrolls(String id) {
		return getPayrolls(id).stream()
				.sorted((a, b) -> b.date.compareTo(a.date))
				.limit(12)
				.toList();
	}

	private List<PayrollRecord> getPayrolls(String id) {
		return payrollRepo.findByContractId(id);
	}
	    
}