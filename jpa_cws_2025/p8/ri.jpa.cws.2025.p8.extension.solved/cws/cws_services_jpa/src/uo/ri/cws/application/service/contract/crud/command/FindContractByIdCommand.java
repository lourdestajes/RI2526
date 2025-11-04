package uo.ri.cws.application.service.contract.crud.command;

import java.util.Optional;

import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class FindContractByIdCommand implements Command<Optional<ContractDto>> {

	public FindContractByIdCommand(String id) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Optional<ContractDto> execute() throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
