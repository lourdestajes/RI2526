package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;


public class AddContractType implements Command<ContractTypeDto> {

	private ContractTypeDto dto = null;
	private ContractTypeGateway ctRepo = Factories.persistence.forContractType();
	private DtoAssembler assembler = new DtoAssembler();
	
	public AddContractType(ContractTypeDto arg) {
		ArgumentChecks.isNotNull(arg, "argument cannot be null");
		ArgumentChecks.isNotNull(arg.name, "name cannot be empty");
		ArgumentChecks.isNotBlank(arg.name, "name cannot be empty");
		ArgumentChecks.isTrue(arg.compensationDays>=0, "compensation days cannot be negative");

		this.dto = arg;
	}

	 
	public ContractTypeDto execute() throws BusinessException {
		Optional<ContractTypeRecord> optionalCt = ctRepo.findByName(this.dto.name);
		BusinessChecks.doesNotExist(optionalCt, 
				"Trying to add a repeated contract type");
		
		ContractTypeRecord ct = assembler.toRecord(dto);
		ctRepo.add( ct );
		
		dto.id = ct.id;
		dto.version = ct.version;
		return dto; 
	}

}
