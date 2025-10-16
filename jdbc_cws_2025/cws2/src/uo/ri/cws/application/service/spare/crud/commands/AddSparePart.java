package uo.ri.cws.application.service.spare.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.spare.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddSparePart implements Command<SparePartDto> {

	private SparePartDto dto = new SparePartDto();
	private SparePartGateway gateway = Factories.persistence.forSparePart();
	private DtoAssembler assembler = new DtoAssembler();

	public AddSparePart(SparePartDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.code, "Code cannot be empty");
		ArgumentChecks.isNotBlank(dto.description,
				"Description cannot be empty");
		this.dto = dto;
	}

	 
	public SparePartDto execute() throws BusinessException {
		checkValidDto(dto);
		checkNotRepeated( dto.code );

		dto.id = UUID.randomUUID().toString();
		dto.version = 0L;
        SparePartRecord record = assembler.toRecord( dto );
		
		gateway.add( record );

		return dto;
	}

	private void checkNotRepeated(String code) throws BusinessException {
		Optional<SparePartRecord> os = gateway.findByCode( code );
		BusinessChecks.isTrue(os.isEmpty(), 
				"There exists a spare parte with the same code");
	}

	private void checkValidDto(SparePartDto dto) throws BusinessException {
		BusinessChecks.isTrue(dto.price >= 0, "price cannot be negative");
		BusinessChecks.isTrue(dto.stock >= 0, "stock cannot be negative");
		BusinessChecks.isTrue(dto.minStock >= 0, "min stock cannot be negative");
		BusinessChecks.isTrue(dto.maxStock >= 0, "max stock cannot be negative");
	}

}
