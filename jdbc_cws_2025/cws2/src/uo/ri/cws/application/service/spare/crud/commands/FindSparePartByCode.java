package uo.ri.cws.application.service.spare.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.spare.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindSparePartByCode implements Command< Optional<SparePartDto> > {
	private String code;
	private SparePartGateway spg = Factories.persistence.forSparePart();
	private DtoAssembler assembler = new DtoAssembler();

	public FindSparePartByCode(String code) {
		ArgumentChecks.isNotNull(code);
		this.code = code;
	}

	 
	public Optional<SparePartDto> execute() throws BusinessException {
		Optional<SparePartRecord> osp = spg.findByCode(code);
		return osp.map( assembler::toDto );
	}

}
