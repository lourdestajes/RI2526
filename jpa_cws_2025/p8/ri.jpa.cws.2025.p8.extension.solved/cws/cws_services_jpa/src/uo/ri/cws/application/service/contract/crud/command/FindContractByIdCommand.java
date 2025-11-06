package uo.ri.cws.application.service.contract.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindContractByIdCommand implements Command<Optional<ContractDto>> {

	private String contractId;
	private ContractRepository repo = Factories.repository.forContract();

	public FindContractByIdCommand(String id) {
		ArgumentChecks.isNotBlank(id, "Invalid null or blank id");
		contractId = id;	
	}

	@Override
	public Optional<ContractDto> execute() throws BusinessException {
		return DtoAssembler.toOptionalDto( repo.findById(contractId) );
	}

	private Optional<Contract> checkExists(String contractId) throws BusinessException {
		Optional<Contract> o = repo.findById(contractId);
		BusinessChecks.exists(o, "There is no contract with this id");
		return o;
	}
	
}
