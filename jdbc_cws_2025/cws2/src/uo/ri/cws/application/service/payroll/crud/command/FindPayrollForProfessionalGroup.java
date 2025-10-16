package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollForProfessionalGroup implements Command<List<PayrollSummaryDto>> {

	private ProfessionalGroupGateway pgRepo = Factories.persistence.forProfessionalGroup();
	private PayrollGateway pRepo = Factories.persistence.forPayroll();
	private ContractGateway cRepo = Factories.persistence.forContract();
	private DtoAssembler assembler = new DtoAssembler();

	private String name = null;
	
	public FindPayrollForProfessionalGroup(String arg) {
		ArgumentChecks.isNotBlank(arg, "Invalid argument");
		name = arg;
	}

	 
	public List<PayrollSummaryDto> execute() throws BusinessException {
		Optional<ProfessionalGroupRecord> opg = pgRepo.findByName(name); 
		BusinessChecks.exists(opg, "Professional Group does not exist");
		
		List<ContractRecord> contracts = cRepo.findByProfessionalGroupId(opg.get().id);
		List<PayrollRecord> payrolls = contracts.stream()
				.flatMap( contract -> pRepo.findByContractId(contract.id).stream() )
				.toList(); 
				
		return assembler.toSummaryDtoList(payrolls);
	}

}