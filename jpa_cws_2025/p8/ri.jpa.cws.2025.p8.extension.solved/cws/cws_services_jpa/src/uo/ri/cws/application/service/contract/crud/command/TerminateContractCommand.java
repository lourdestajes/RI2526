package uo.ri.cws.application.service.contract.crud.command;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class TerminateContractCommand implements Command<Void> {

	private String contractId;
	private ContractRepository repo = Factories.repository.forContract();

	/**
	 * It changes contract to TERMINATED and calculates settlement according 
	 * the rules described in the problem statement document.
	 * 
	 * @param contractId, 
	 * @throws BusinessException when, 
	 * 		- The contract does not exist, or
	 * 		- the contract is not in force, or
	 * @throws IllegalArgumentException when
 	 * 		- id is null or empty, or
	 */
	public TerminateContractCommand(String contractId) {
		ArgumentChecks.isNotBlank(contractId, "Invalid null or blank id");
		this.contractId = contractId;
	}

	@Override
	public Void execute() throws BusinessException {
		Contract c = checkExists(contractId);
		canBeTerminated(c);
		c.terminate(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
		return null;
	}

	private Contract checkExists(String contractId) throws BusinessException {
		Optional<Contract> o = repo.findById(contractId);
		BusinessChecks.exists(o, "There is no contract with this id");
		return o.get();
	}

	private void canBeTerminated(Contract c) throws BusinessException {
		BusinessChecks.isTrue(c.isInForce(), "Contract is not in force. Cannot be Terminated");
	}
	
}
