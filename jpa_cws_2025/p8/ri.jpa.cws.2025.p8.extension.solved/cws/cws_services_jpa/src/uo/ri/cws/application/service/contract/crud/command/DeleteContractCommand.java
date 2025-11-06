package uo.ri.cws.application.service.contract.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

/**
 * It deletes the contract, if possible.
 * 
 * @param id Contract identifier
 * @throws BusinessException when:
 * 		- The contract does not exist, or
 * 		- mechanic has workorders, or
 * 		- there are payrolls for this contract.
 * @throws IllegalArgumentException when
	 * 		- id is null or empty.
 */
public class DeleteContractCommand implements Command<Void> {

	private String contractId;
	private ContractRepository repo = Factories.repository.forContract();

	public DeleteContractCommand(String id) {
		ArgumentChecks.isNotBlank(id, "Invalid null or blank id");
		contractId = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Contract c = checkExists(contractId);
		checkCanBeDeleted(c);
		repo.remove(c);
		return null;
	}

	private void checkCanBeDeleted(Contract c) throws BusinessException {
		BusinessChecks.isTrue(c.getMechanic().getAssigned().isEmpty(), "Contract cannot be deleted");
		BusinessChecks.isTrue(c.getMechanic().getInterventions().isEmpty(), "Contract cannot be deleted");
		BusinessChecks.isTrue(c.getPayrolls().isEmpty(), "Contract cannot be deleted");
	}

	private Contract checkExists(String contractId2) throws BusinessException {
		Optional<Contract> o = repo.findById(contractId);
		BusinessChecks.exists(o, "There is no contract with this id");
		return o.get();
	}


}
