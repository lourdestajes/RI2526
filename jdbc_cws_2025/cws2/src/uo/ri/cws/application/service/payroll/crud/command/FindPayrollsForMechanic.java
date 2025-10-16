package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollsForMechanic implements Command<List<PayrollSummaryDto>> {

	private String id = null;
	private PayrollGateway pRepo = Factories.persistence.forPayroll();
	private MechanicGateway mRepo = Factories.persistence.forMechanic();
	private ContractGateway cRepo = Factories.persistence.forContract();
	private DtoAssembler assembler = new DtoAssembler();

	public FindPayrollsForMechanic(String arg) {
		ArgumentChecks.isNotNull(arg, "Null argument");
		ArgumentChecks.isNotBlank(arg, "Invalid argument");
		id = arg;
	}

	 
	public List<PayrollSummaryDto> execute() throws BusinessException {
		BusinessChecks.exists( mRepo.findById(id), "Mechanic does not exist");
		List<ContractRecord> contracts = cRepo.findByMechanicId(id);

		List<PayrollRecord> payrolls = contracts.stream()
				.flatMap( contract -> pRepo.findByContractId(contract.id).stream() )
				.toList(); 
				
		return assembler.toSummaryDtoList(payrolls);		
	}

}
