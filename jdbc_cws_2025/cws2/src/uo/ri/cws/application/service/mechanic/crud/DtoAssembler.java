package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class DtoAssembler {

	public MechanicDto toDto(MechanicRecord record ) {
		MechanicDto dto = new MechanicDto();
		dto.id = record.id;
		dto.version = record.version;
		
		dto.nif = record.nif;
		dto.name = record.name;
		dto.surname = record.surname;
		return dto;
	}
	
	public MechanicRecord toRecord(MechanicDto dto ) {
		MechanicRecord res = new MechanicRecord();
		// id and version set by the Record
		
		res.nif = dto.nif;
		res.name = dto.name;
		res.surname = dto.surname;
		return res;
	}

	public List<MechanicDto> toDtoList(List<MechanicRecord> list) {
		return list.stream().map( r -> toDto(r)).toList();
	}

}

