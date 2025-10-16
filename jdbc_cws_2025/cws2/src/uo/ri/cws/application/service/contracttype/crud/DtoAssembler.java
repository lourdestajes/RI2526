package uo.ri.cws.application.service.contracttype.crud;

import java.util.List;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;

public class DtoAssembler {
	
	public ContractTypeDto toDto(ContractTypeRecord arg) {
		ContractTypeDto result = new ContractTypeDto();
		result.id = arg.id;
		result.version = arg.version;
		
		result.name = arg.name;
		result.compensationDays = arg.compensationDaysPerYear;
		return result;
	}

	public ContractTypeRecord toRecord(ContractTypeDto dto) {
		ContractTypeRecord res = new ContractTypeRecord();		
		res.compensationDaysPerYear = dto.compensationDays;
		res.name = dto.name;
		return res;
	}

	public List<ContractTypeDto> toDtoList(List<ContractTypeRecord> all) {
		return all.stream().map( r -> toDto(r)).toList();
	}

}
