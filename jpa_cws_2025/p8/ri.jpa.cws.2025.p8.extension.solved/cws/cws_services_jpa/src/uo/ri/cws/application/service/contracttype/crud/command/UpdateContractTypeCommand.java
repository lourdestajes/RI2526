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

/**
 * Updates just the compensation days
 * @param dto
 * @throws IllegalArgumentException if
 * 		- arg is null
 * 		- nanme is null or empty
 * 		- the number of compensation days is negative
 * @throws BusinessException if:
 * 		- the contract type does not exist, or
 */
public class UpdateContractTypeCommand implements Command<Void> {

	private ContractTypeRepository repo = Factories.repository.forContractType();
	private ContractTypeDto dto;

	public UpdateContractTypeCommand(ContractTypeDto dto) {
		ArgumentChecks.isNotNull(dto, "Invalid null dto");
		ArgumentChecks.isNotBlank(dto.name, "Name cannot be null or blank");
		ArgumentChecks.isTrue(dto.compensationDays > 0, "Compensation cannot be negative");
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractType type = checkTypeExists(dto.name);
		BusinessChecks.hasVersion(dto.version, type.getVersion());
		type.setCompensationDays(dto.compensationDays);
		type.updatedNow();
		return null;
	}
	private ContractType checkTypeExists(String name) throws BusinessException {
		Optional<ContractType> o = repo.findByName(name);
		BusinessChecks.exists(o, "ContractType does not exist");
		return o.get();
	}
}

