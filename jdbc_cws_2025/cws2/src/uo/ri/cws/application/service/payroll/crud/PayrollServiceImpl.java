package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.command.DeleteLastPayrollFor;
import uo.ri.cws.application.service.payroll.crud.command.DeleteLastPayrolls;
import uo.ri.cws.application.service.payroll.crud.command.FindAllPayrolls;
import uo.ri.cws.application.service.payroll.crud.command.FindPayrollDetails;
import uo.ri.cws.application.service.payroll.crud.command.FindPayrollForProfessionalGroup;
import uo.ri.cws.application.service.payroll.crud.command.FindPayrollsForMechanic;
import uo.ri.cws.application.service.payroll.crud.command.GeneratePayroll;
import uo.ri.util.exception.BusinessException;

public class PayrollServiceImpl implements PayrollService {

	private CommandExecutor executor = new CommandExecutor();

	 
	public List<PayrollDto> generateForPreviousMonth() throws BusinessException {
		return executor.execute(new GeneratePayroll());
	}

	 
	public List<PayrollDto> generateForPreviousMonthOf(LocalDate date) throws BusinessException {
		return executor.execute(new GeneratePayroll(date));
	}

	 
	public void deleteLastGeneratedOfMechanicId(String mechanicId) throws BusinessException {
		executor.execute(new DeleteLastPayrollFor(mechanicId));
	}

	 
	public int deleteLastGenerated() throws BusinessException {
		return executor.execute(new DeleteLastPayrolls());
	}

	 
	public Optional<PayrollDto> findById(String id) throws BusinessException {
		return executor.execute(new FindPayrollDetails( id ));
		
	}

	 
	public List<PayrollSummaryDto> findAllSummarized() throws BusinessException {
		return executor.execute(new FindAllPayrolls( ));
		
	}

	 
	public List<PayrollSummaryDto> findSummarizedByMechanicId(String id) throws BusinessException {
		return executor.execute(new FindPayrollsForMechanic( id ));
		
	}

	 
	public List<PayrollSummaryDto> findSummarizedByProfessionalGroupName(String name) throws BusinessException {
		return executor.execute(new FindPayrollForProfessionalGroup( name ));
		
	}

}
