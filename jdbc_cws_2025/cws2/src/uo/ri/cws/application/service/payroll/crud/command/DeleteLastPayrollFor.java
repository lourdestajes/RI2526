package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastPayrollFor implements Command<Void> {

	private String id;
	private MechanicGateway mRepo = Factories.persistence.forMechanic();
	private PayrollGateway pRepo = Factories.persistence.forPayroll();
	private ContractGateway cRepo = Factories.persistence.forContract();

	
	public DeleteLastPayrollFor(String arg) {
		ArgumentChecks.isNotBlank(arg, "Mechanic id cannot be null, blank or empty");
		id = arg;
	}

	 
	public Void execute() throws BusinessException {
		BusinessChecks.exists(mRepo.findById(id));
		List<ContractRecord> contracts = cRepo
				.findByMechanicId(id)
				.stream()
				.filter(c -> "IN_FORCE".equals(c.state))
				.toList();
		if ( contracts.isEmpty() ) {
			return null;
		}
		ContractRecord inForce = contracts.get(0);
		
		Optional<PayrollRecord> op = pRepo.findLastMonthPayrollForContract(inForce.id);
		if ( op.isEmpty() ) {
			return null;
		}
		
		pRepo.remove( op.get().id );
		return null;				
	}	

}
