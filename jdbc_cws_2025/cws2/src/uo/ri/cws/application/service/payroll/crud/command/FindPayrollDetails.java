package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayrollDetails implements Command<Optional<PayrollDto>> {

	private String id = null;
	private PayrollGateway pRepo = Factories.persistence.forPayroll();
	private DtoAssembler assembler = new DtoAssembler();

	public FindPayrollDetails(String arg) {
		ArgumentChecks.isNotNull(arg, "Cannot search a null id payroll");
		ArgumentChecks.isNotBlank(arg, "Invalid payroll to search");
		id = arg;
	}

	 
	public Optional<PayrollDto> execute() throws BusinessException {
		Optional<PayrollRecord> optional = pRepo.findById(id);
		PayrollDto result = null;
		if (optional.isPresent()) {
			PayrollRecord p = optional.get();
			result = assembler.toDto(p);
			result.netSalary = result.baseSalary
					+ result.extraSalary
					+ result.productivityEarning
					+ result.trienniumEarning;
			result.netSalary = result.netSalary
					- result.taxDeduction
					- result.nicDeduction;
		}
		return Optional.ofNullable(result); 
	}

}
