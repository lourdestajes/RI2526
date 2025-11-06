package uo.ri.cws.application.service.contract.crud.command;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractTypeOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.MechanicOfContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ProfessionalGroupOfContractDto;
import uo.ri.cws.application.service.contract.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
/**
 * Creates a new contract for a given mechanic. 
 * If there already exists an earlier contract for the same mechanic, 
 * and that is in force, it will be marked as terminated and the settlement 
 * will be computed. Contract start date will be the first day of the next month.
 *  
 * @param c, contract business DTO. Consider only fields mechanic dni, type name,
 * professionalGroup name, startDate, endDate (if not null) and annualSalary. 
 * Others will be ignored.
 * 
 * @return a fully filled DTO, including its secondary DTO.
 * 
 * @throws BusinessException when:
 * 		- Contract type does not exist, or 
 * 		- mechanic does not exist, or 
 * 		- professional group does not exist, or
 * 		- end date, when no null, is not later than start date.
 * @throws IllegalArgumentException when:
 * 		- argument is null, or
 * 		- one of the mandatory values is null or empty String, or
 * 		- professional group is FIXED_TERM and end date is null, or
 * 		- annualBaseWage is not greater than 0
 */
public class CreateContractCommand implements Command<ContractDto> {

	private ContractDto dto;
	private ContractTypeRepository typeRepo = Factories.repository.forContractType();
	private ProfessionalGroupRepository groupRepo = Factories.repository.forProfessionalGroup();
	private MechanicRepository mechRepo = Factories.repository.forMechanic();
	private ContractRepository contractRepo = Factories.repository.forContract();

	public CreateContractCommand(ContractDto c) {
		ArgumentChecks.isNotNull(c, "Invalid null contract");
		ArgumentChecks.isNotNull(c.contractType, "Invalid null contract type");
		ArgumentChecks.isNotNull(c.professionalGroup, "Invalid null professional group");
		ArgumentChecks.isNotNull(c.mechanic, "Invalid null mechanic");
		ArgumentChecks.isNotBlank(c.contractType.name, "Invalid blank contract type name");
		ArgumentChecks.isNotBlank(c.professionalGroup.name, "Invalid blank professional group name");
		ArgumentChecks.isNotBlank(c.mechanic.nif, "Invalid blank mechanic nif");
		if ("FIXED_TERM".equals(c.professionalGroup.name)) {
			ArgumentChecks.isNotNull(c.endDate);
		}
		ArgumentChecks.isTrue(c.annualBaseSalary > 0, "Invalid annual base salary is negative");
		this.dto = c;
	}

	@Override
	public ContractDto execute() throws BusinessException {
		ContractType type = checkTypeExists(dto.contractType);
		ProfessionalGroup group = checkGroupExists(dto.professionalGroup);
		Mechanic mech = checkMechanicExists(dto.mechanic);
		validateDate(dto);
		LocalDate start = firstDayOfNextMonth(dto.startDate);
		LocalDate end = lastDayOfMonth(dto.endDate);
		
		Contract c = new Contract(mech, type, group, start, end, dto.annualBaseSalary);  
		contractRepo.add(c);
		return DtoAssembler.toDto(c);
	}

	private LocalDate lastDayOfMonth(LocalDate endDate) {
			return "FIXED_TERM".equals(dto.contractType.name) ? endDate : null;
	}

	private ContractType checkTypeExists(ContractTypeOfContractDto arg) throws BusinessException {
		Optional<ContractType> o = typeRepo.findByName(arg.name);
		BusinessChecks.exists(o, "Contract Type does not exist");
		return o.get();
	}

	private ProfessionalGroup checkGroupExists(ProfessionalGroupOfContractDto arg) throws BusinessException {
		Optional<ProfessionalGroup> o = groupRepo.findByName(arg.name);
		BusinessChecks.exists(o, "ProfessionalGroup does not exist");
		return o.get();
	}
	
	private Mechanic checkMechanicExists(MechanicOfContractDto arg) throws BusinessException {
		Optional<Mechanic> o = mechRepo.findByNif(arg.nif);
		BusinessChecks.exists(o, "Mechanic does not exist");
		return o.get();
	}
	
	private void validateDate(ContractDto contract) throws BusinessException {
		if (contract.endDate != null) {
			BusinessChecks.isTrue(contract.endDate.isAfter(contract.startDate));
		}
		
	}
	
	private LocalDate firstDayOfNextMonth(LocalDate d) {
		return d.plusMonths(1)
				.with(TemporalAdjusters.firstDayOfMonth());
	}

}
