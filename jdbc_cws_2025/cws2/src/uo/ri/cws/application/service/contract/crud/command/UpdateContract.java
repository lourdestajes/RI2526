package uo.ri.cws.application.service.contract.crud.command;

import java.time.LocalDate;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateContract implements Command<ContractDto> {
	private ContractDto dto;
	private ContractRecord contract;

	private ContractGateway contractRepo = Factories.persistence.forContract();
	private ContractTypeGateway contractTypeRepo = Factories.persistence.forContractType();


	public UpdateContract(ContractDto arg) {
		ArgumentChecks.isNotNull(arg, "Contract cannot be null");
		ArgumentChecks.isNotNull(arg.id, "Contract id cannot be null");
		ArgumentChecks.isNotBlank(arg.id, "Contract id cannot be empty");
		ArgumentChecks.isTrue(arg.annualBaseSalary > 0 , "Annual base wage cannot be negative");

		this.dto = arg;
	}

	 
	public ContractDto execute() throws BusinessException {
		checkExists( );
		checkCanBeUpdated();

		LocalDate d = (dto.endDate!=null)?dto.endDate:contract.endDate;
		BusinessChecks.hasVersion(contract.version, dto.version);
		
		contract.endDate = d;
		contract.annualBaseSalary = dto.annualBaseSalary;
		contractRepo.update(contract);

		return dto;

	}

	/*
	 * Check contract exists
	 * Check contract type exists
	 * Check professional group exists
	 * Check end date is later than start date
	 *
	 */
	private void checkCanBeUpdated( ) throws BusinessException {
		BusinessChecks.isTrue("IN_FORCE".equals(contract.state),
				"Cannot be updated, it is no longer in force");

		String contractTypeName = contractTypeRepo.findById(contract.contractTypeId).get().name;
		if ("FIXED_TERM".equals(contractTypeName)) {
			BusinessChecks.isTrue (this.contract.startDate.isBefore( this.dto.endDate ),
					"Cannot be updated, End date must be later than start date");			
		}
	}

	/*
	 * Check contract exists
	 */
	private void checkExists( ) throws BusinessException {
		Optional<ContractRecord> op = contractRepo.findById(dto.id);
		BusinessChecks.exists( op, "Contract does not exist");
		this.contract = op.get();
	}


}
