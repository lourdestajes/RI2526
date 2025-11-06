package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.domain.ProfessionalGroup;

public class DtoAssembler {


	public static Optional<ProfessionalGroupDto> toOptionalDto(Optional<ProfessionalGroup> arg) {
		ProfessionalGroupDto dto = null;
		
		if (arg.isPresent()) {
			dto = toDto(arg.get());
		}
		return Optional.ofNullable(dto);
	}

	public static List<ProfessionalGroupDto> toDtoList(List<ProfessionalGroup> list) {
		return list.stream().map(DtoAssembler::toDto).toList();
	}
	
	private static ProfessionalGroupDto toDto(ProfessionalGroup m) {
		ProfessionalGroupDto 	dto = new ProfessionalGroupDto();
		dto.id = m.getId();
		dto.version = m.getVersion();

		dto.name = m.getName();
		dto.productivityRate = m.getProductivityRate();
		dto.trienniumPayment = m.getTrienniumPayment();
		return dto;
	}


}
