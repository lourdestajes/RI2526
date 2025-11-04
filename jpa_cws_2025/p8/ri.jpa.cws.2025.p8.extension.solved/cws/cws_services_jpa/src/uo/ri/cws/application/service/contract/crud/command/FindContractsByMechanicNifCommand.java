package uo.ri.cws.application.service.contract.crud.command;

import java.util.List;

import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class FindContractsByMechanicNifCommand implements Command<List<ContractSummaryDto>> {

	public FindContractsByMechanicNifCommand(String nif) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ContractSummaryDto> execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
