package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.util.exception.BusinessException;

public class FindAllPayrolls implements Command<List<PayrollSummaryDto>> {

	private PayrollGateway repo = Factories.persistence.forPayroll();
	private DtoAssembler assembler = new DtoAssembler();
	
	 
	public List<PayrollSummaryDto> execute() throws BusinessException {
		List<PayrollRecord> inDatabase = repo.findAll();
		return assembler.toSummaryDtoList(inDatabase);
	}

}
