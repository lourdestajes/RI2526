package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.DtoAssembler;
import uo.ri.util.exception.BusinessException;

public class FindAllProfessionalGroups implements Command<List<ProfessionalGroupDto>> {

	private ProfessionalGroupGateway repo = Factories.persistence.forProfessionalGroup();
	private DtoAssembler assembler = new DtoAssembler();

	 
	public List<ProfessionalGroupDto> execute() throws BusinessException {
		return assembler.toDtoList(repo.findAll());
	}

}
