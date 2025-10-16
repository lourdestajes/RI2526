package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteContractType implements Command<Void> {

	private String name2Del;
	private ContractTypeGateway ctRepo = Factories.persistence.forContractType();
	private ContractGateway cRepo = Factories.persistence.forContract();
	
	public DeleteContractType(String arg) {
		ArgumentChecks.isNotNull(arg, "argument cannot be null");
		ArgumentChecks.isNotBlank(arg, "arg cannot be empty");
		
		this.name2Del = arg;
	}

	 
	public Void execute() throws BusinessException {
		Optional<ContractTypeRecord> optional = ctRepo.findByName(this.name2Del);
		BusinessChecks.exists( optional, "Trying to del a non existent ContractType");
		String id2del = optional.get().id;
		List<ContractRecord> contracts = cRepo.findByContractTypeId(id2del);
		BusinessChecks.isTrue ( contracts.isEmpty(), 
				"Trying to del a contract type with contracts");
		ctRepo.remove(id2del);
		return null;
	}

}
