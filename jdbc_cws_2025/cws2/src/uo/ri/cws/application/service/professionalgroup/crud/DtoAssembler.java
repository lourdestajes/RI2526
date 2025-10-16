package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;

public class DtoAssembler {

	public ProfessionalGroupDto toDto(ProfessionalGroupRecord m) {
		ProfessionalGroupDto dto = new ProfessionalGroupDto();
		dto.id = m.id;
		dto.version = m.version;
		
		dto.name = m.name;
		dto.productivityRate = m.productivityRate;
		dto.trienniumPayment = m.trienniumPayment;
		return dto;
	}
	
	public ProfessionalGroupRecord toRecord(ProfessionalGroupDto dto) {
		ProfessionalGroupRecord record = new ProfessionalGroupRecord();
		record.name = dto.name;
		record.productivityRate = dto.productivityRate;
		record.trienniumPayment = dto.trienniumPayment;
		return record;
	}

	public List<ProfessionalGroupDto> toDtoList(List<ProfessionalGroupRecord> all) {
		return all.stream().map(this::toDto).toList();
	}

}
