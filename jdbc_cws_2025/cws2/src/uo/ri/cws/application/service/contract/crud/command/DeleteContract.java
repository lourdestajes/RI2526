package uo.ri.cws.application.service.contract.crud.command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContract implements Command<Void> {

	private String id = null;
	private ContractGateway contractG = Factories.persistence.forContract();
	private InterventionGateway interventionG = Factories.persistence.forIntervention();
	private PayrollGateway payrollG = Factories.persistence.forPayroll();

	private ContractRecord c = null;
	
	public DeleteContract(String arg) {
		ArgumentChecks.isNotNull(arg, "Contract id cannot be null");
		ArgumentChecks.isNotBlank(arg, "Contract id cannot be empty");
		this.id = arg;
	}

	 
	public Void execute() throws BusinessException {
		assertContractExists(id);
		assertCanBeDeleted();

		contractG.remove( c.id );
		return null;
	}

	/*
	 * check there is no intervention for the contract
	 * check there is no payroll for the contract
	 */
	private void assertCanBeDeleted() throws BusinessException {
		checkMechanicHasNoInterventions(c.mechanicId);
		checkContractHasNoPayrolls(c.id);		
	}

	/*
	 * check contract exists
	 */
	private void assertContractExists(String id) throws BusinessException {
		Optional<ContractRecord> op = contractG.findById(id);
		BusinessChecks.exists( op, "Contract does not exist");
		this.c = op.get();
	}


	private void checkContractHasNoPayrolls(String id) throws BusinessException {
		List<PayrollRecord> payrolls = payrollG.findByContractId(id);
		BusinessChecks.isEmpty( payrolls
				, "Contract cannot be deleted due to existing payrolls");
	}

	private void checkMechanicHasNoInterventions(String m) throws BusinessException {
		LocalDateTime start = c.startDate.atStartOfDay();
		LocalDateTime end = LocalDateTime.now();

		List<InterventionRecord> interventions = interventionG.findByMechanicBetweenDates(m, start, end);
		BusinessChecks.isEmpty(interventions, "Contract cannot be deleted due to workorders during contract");
		
	}
}
