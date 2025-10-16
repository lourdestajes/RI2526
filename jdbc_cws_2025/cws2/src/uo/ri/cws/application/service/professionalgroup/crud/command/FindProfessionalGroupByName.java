package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindProfessionalGroupByName implements Command<Optional<ProfessionalGroupDto>> {

	private String id = null;
	private ProfessionalGroupGateway pgg = Factories.persistence.forProfessionalGroup();
	private DtoAssembler assembler = new DtoAssembler();

	public FindProfessionalGroupByName(String name) {
		ArgumentChecks.isNotNull(name, "Null id");
		ArgumentChecks.isNotBlank(name, "Empty id");
		this.id  = name;
	}

	 
	public Optional<ProfessionalGroupDto> execute() throws BusinessException {
		return pgg.findByName(id).map(assembler::toDto);
		 
	}

}
