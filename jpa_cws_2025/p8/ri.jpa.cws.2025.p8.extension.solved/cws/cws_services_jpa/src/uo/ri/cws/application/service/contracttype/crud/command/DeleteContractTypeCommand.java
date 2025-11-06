package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

/**
 * Deletes a contract type if possible
 * @param name of the contract type
 * @throws IllegalArgumentException if
 * 		- name is null or empty
 * @throws BusinessException if 
 * 		- the contract type does not exist, or
 * 		- there are contracts registered with the contract type
 */
public class DeleteContractTypeCommand implements Command<Void> {

	private String name;
	private ContractTypeRepository repo = Factories.repository.forContractType();
	
	public DeleteContractTypeCommand(String name) {
		ArgumentChecks.isNotBlank(name, "Invalid contract type name");
		this.name = name;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractType type = checkTypeExists(name);
		canBeDeleted(type);
		repo.remove(type);
		return null;
	}

	private void canBeDeleted(ContractType type) throws BusinessException {
		BusinessChecks.isTrue(type.getContracts().isEmpty());
	}

	private ContractType checkTypeExists(String name) throws BusinessException {
		Optional<ContractType> o = repo.findByName(name);
		BusinessChecks.exists(o, "ContractType does not exist");
		return o.get();
	}

}
