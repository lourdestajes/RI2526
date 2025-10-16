package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByName implements Command<Optional<ContractTypeDto>> {
	
	private String name = null;
	private DtoAssembler assembler = new DtoAssembler();
	private ContractTypeGateway ctRepo = Factories.persistence.forContractType();
	
	/**
	 * @param arg, of the contract type
	 * @throws IllegalArgumentException if
	 * 		- arg is null or empty
	 * @return the dto with all the fields set, or
	 * 		empty if does not exist the contract type
	 * @throws BusinessException DOES NOT
	 */
	public FindByName(String arg) {
		ArgumentChecks.isNotNull(arg, "null arg");
		ArgumentChecks.isNotBlank(arg, "empty arg");
		this.name  = arg;
	}

	 
	public Optional<ContractTypeDto> execute() throws BusinessException {
		return ctRepo.findByName(name).map( c -> assembler.toDto(c) );
	}

}
