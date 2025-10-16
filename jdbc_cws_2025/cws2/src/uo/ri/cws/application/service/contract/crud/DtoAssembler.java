package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;

public class DtoAssembler {

	public ContractDto toDto(ContractRecord arg) {
		ContractDto result = new ContractDto();
		result.id = arg.id;
		result.version = arg.version;
		
		result.taxRate = arg.taxRate;
		result.annualBaseSalary = arg.annualBaseSalary;
		result.startDate = arg.startDate;
		result.endDate = arg.endDate;
		result.state = arg.state.toString();
		result.settlement = arg.settlement;
		
		result.contractType.id = arg.contractTypeId;	
		result.professionalGroup.id = arg.professionalGroupId;		
		result.mechanic.id = arg.mechanicId;
		return result;
	}

	public ContractDto toFullDto(ContractRecord c, MechanicRecord m, ContractTypeRecord ct,
			ProfessionalGroupRecord pg) {
		ContractDto result = toDto(c);
		
		result.mechanic.id = m.id;
		result.mechanic.nif = m.nif;
		result.mechanic.name = m.name;
		result.mechanic.surname = m.surname;
		
		result.contractType.id = ct.id;
		result.contractType.name = ct.name;
		result.contractType.compensationDaysPerYear = ct.compensationDaysPerYear;
		
		result.professionalGroup.id = pg.id;
		result.professionalGroup.name = pg.name;
		result.professionalGroup.productivityRate = pg.productivityRate;
		result.professionalGroup.trieniumPayment = pg.trienniumPayment;
		
		return result;
	}

	public ContractSummaryDto toContractSummaryDto(ContractRecord arg) {
		ContractSummaryDto result = new ContractSummaryDto();
		result.id = arg.id;
		result.state = arg.state;
		result.settlement = arg.settlement;

		return result;
	}
	
	public ContractRecord toRecord(ContractDto dto) {
		ContractRecord result = new ContractRecord();
		// id, version and entityState set by the Record
		
		result.annualBaseSalary = dto.annualBaseSalary;
		result.settlement = dto.settlement;
		result.taxRate = dto.taxRate;
		result.startDate = dto.startDate;
		result.endDate = dto.endDate;
		result.state = dto.state;

		result.mechanicId = dto.mechanic.id;
		result.professionalGroupId = dto.professionalGroup.id;
		result.contractTypeId = dto.contractType.id;
		return result;
	}
	
	public List<ContractSummaryDto> toContractSummaryDtoList(List<ContractRecord> arg) {
		return arg.stream()
				.map(c -> toContractSummaryDto(c) )
				.collect( Collectors.toList() );
	}

}
