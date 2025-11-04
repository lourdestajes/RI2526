package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class FindContractTypeByNameCommand implements Command<Optional<ContractTypeDto>> {

	public FindContractTypeByNameCommand(String name) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Optional<ContractTypeDto> execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
