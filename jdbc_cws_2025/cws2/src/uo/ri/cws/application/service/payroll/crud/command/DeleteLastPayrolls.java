package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class DeleteLastPayrolls implements Command<Integer> {

	private PayrollGateway repo  = Factories.persistence.forPayroll();
	
	 
	public Integer execute() throws BusinessException {
		List<PayrollRecord> toRemove = repo.findLastMonthPayrolls();
		for (PayrollRecord p : toRemove) {
			repo.remove(p.id);
		}
		return toRemove.size();
	}

}
