package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.DtoAssembler;
import uo.ri.util.exception.BusinessException;

public class FindAll implements Command<List<ContractTypeDto>> {

	private ContractTypeGateway ctRepo = Factories.persistence.forContractType();
	private DtoAssembler assembler = new DtoAssembler();

	 
	public List<ContractTypeDto> execute() throws BusinessException {
		
		return assembler.toDtoList(ctRepo.findAll());
	}

}
