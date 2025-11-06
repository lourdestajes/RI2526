package uo.ri.cws.application.service.contract.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

/**
 * It updates a contract that must be in force.
 * Only three of the fields in the argument will be considered: id to identify 
 * the contract to update and endDate (if not null in the argument) and 
 * annualBaseWage, to update values stored. 
 * 
 * If endDate provided is not null, it must be a valid future date and will 
 * be updated. If it is null, then contract endDate will be set to null.
 * 
 * @param dto, just id, endDate and annualBaseWage are mandatory. Other 
 * fields in the argument will be ignored.
 * 
 * @throws BusinessException when:
 * 		- The contract does not exist, or
 * 		- the contract is no longer in force, or
 * 		- contract type is FIXED_TERM and end date is earlier than startDate, or
 * 		- the contract is no longer in force.
 * @throws IllegalArgumentException when:
 * 		- arg is null, or
	 * 		- id is null or empty, or
 * 		- the annualBaseWage is a negative value.
 */
public class UpdateContractCommand implements Command<Void> {

	private ContractDto dto;
	private ContractRepository repo = Factories.repository.forContract();

	public UpdateContractCommand(ContractDto dto) {
		ArgumentChecks.isNotNull(dto, "Command Update Contract : Invalid null dto" );
		ArgumentChecks.isNotBlank(dto.id, "Command Update Contract : Invalid null or blank id" );
		ArgumentChecks.isTrue(dto.annualBaseSalary > 0, "Command Update Contract : Invalid annual base salary" );
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Contract c = checkExists(dto.id);
		canBeUpdated(c);
		c.setAnnualBaseSalary(dto.annualBaseSalary);
		c.setEndDate(dto.endDate);
		c.updatedNow();
		return null;
	}
	
	private void canBeUpdated(Contract c) throws BusinessException {
		BusinessChecks.isTrue(c.isInForce(), "Contract cannot be updated");
		if ("FIXED_TERM".equals(c.getContractType().getName())) {
			BusinessChecks.isTrue(dto.endDate.isAfter(c.getStartDate()), "Contract cannot be deleted");			
		}
		BusinessChecks.hasVersion(dto.version, c.getVersion());
	}


	private Contract checkExists(String arg) throws BusinessException {
		Optional<Contract> o = repo.findById(arg);
		BusinessChecks.exists(o, "There is no contract with this id");
		return o.get();
	}
}
