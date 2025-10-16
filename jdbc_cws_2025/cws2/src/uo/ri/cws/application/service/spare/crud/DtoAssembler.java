package uo.ri.cws.application.service.spare.crud;

import uo.ri.cws.application.persistence.sparePart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;

public class DtoAssembler {

	public SparePartDto toDto(SparePartRecord r) {
		SparePartDto dto = new SparePartDto();
		dto.id = r.id;
		dto.version = r.version;
		
		dto.code = r.code;
		dto.description = r.description;
		dto.price = r.price;
		dto.stock = r.stock;
		dto.minStock = r.minStock;
		dto.maxStock = r.maxStock;
		dto.version = r.version;
		
		return dto;
	}

	public SparePartRecord toRecord(SparePartDto dto) {
		SparePartRecord record = new SparePartRecord();
		record.code = dto.code;
		record.description = dto.description;
		record.price = dto.price;
		record.stock = dto.stock;
		record.minStock = dto.minStock;
		record.maxStock = dto.maxStock;
		return record;
	}

}
