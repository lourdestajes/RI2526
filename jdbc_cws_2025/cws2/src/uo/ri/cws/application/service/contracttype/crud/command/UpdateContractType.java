package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateContractType implements Command<Void> {

	private ContractTypeDto dto = null;
	private ContractTypeGateway ctRepo = Factories.persistence.forContractType();

	public UpdateContractType(ContractTypeDto arg) {
		ArgumentChecks.isNotNull(arg, "Dto cannot be null");
		ArgumentChecks.isNotBlank(arg.name, "name cannot be null, empty or blank");
		ArgumentChecks.isTrue(arg.compensationDays >= 0, "compensation days cannot be negative");

		this.dto  = arg;
	}

	 
	public Void execute() throws BusinessException {
		Optional<ContractTypeRecord> optional = ctRepo.findByName(this.dto.name);
		BusinessChecks.exists( optional, "Trying to update a non existent ContractType");
		ContractTypeRecord ct = optional.get();

		BusinessChecks.hasVersion(ct.version, dto.version);
		ct.compensationDaysPerYear = dto.compensationDays;

		ctRepo.update(ct);
		return null;
	}

}