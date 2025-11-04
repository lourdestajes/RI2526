package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class CreateContractTypeCommand implements Command<ContractTypeDto> {

	private ContractTypeDto dto;
	private ContractTypeRepository repo = Factories.repository.forContractType();

	public CreateContractTypeCommand(ContractTypeDto dto) {
        ArgumentChecks.isNotNull ( dto, "Cannot add a null contract type" );
        ArgumentChecks.isNotBlank ( dto.name, "Name cannot be blank" );
        ArgumentChecks.isTrue(dto.compensationDays>0, "Compensation days cannot be negative");
        this.dto = dto;
	}

	@Override
	public ContractTypeDto execute() throws BusinessException {
		checkContractTypeDoesNotExist(dto.name);
		ContractType type = new ContractType(dto.name, dto.compensationDays);
		repo.add(type);
		dto.id = type.getId();
		dto.version = type.getVersion();
		return dto;
	}

	private void checkContractTypeDoesNotExist(String name) throws BusinessException {
		Optional<ContractType> opt = repo.findByName(name);
		BusinessChecks.doesNotExist(opt, "There’s already another contract type with the same name.");
	}

}
