package uo.ri.cws.application.service.spare.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateSparePart implements Command<Void> {

	private SparePartDto dto;
	private SparePartGateway spg = Factories.persistence.forSparePart();

	public UpdateSparePart(SparePartDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank( dto.description );
		ArgumentChecks.isNotNull( dto.code );
		this.dto = dto;
	}

	 
	public Void execute() throws BusinessException {
		checkValidDto( dto );

		Optional<SparePartRecord> os = spg.findByCode( dto.code );
		BusinessChecks.exists(os, "The spare part code does not exist");

		SparePartRecord record = os.get();
		BusinessChecks.hasVersion(record.version, dto.version);
		
		// code and id ignored
		// version upgraded
		record.description = dto.description;
		record.price = dto.price;
		record.stock = dto.stock;
		record.minStock = dto.minStock;
		record.maxStock = dto.maxStock;
		record.version = dto.version + 1;

		spg.update( record );

		return null;
	}

	private void checkValidDto(SparePartDto dto) throws BusinessException {
		BusinessChecks.isNotEmpty(dto.code, "Code cannot be empty");
		BusinessChecks.isNotEmpty(dto.id, "Id cannot be empty");
		BusinessChecks.isNotEmpty(dto.description,"Description cannot be empty");
		BusinessChecks.isTrue(dto.price >= 0, "price cannot be negative");
		BusinessChecks.isTrue(dto.stock >= 0, "stock cannot be negative");
		BusinessChecks.isTrue(dto.minStock >= 0, "min stock cannot be negative");
		BusinessChecks.isTrue(dto.maxStock >= 0, "max stock cannot be negative");
	}

}
