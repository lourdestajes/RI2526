package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.exception.BusinessException;


public class FindAllProfessionalGroupsCommand implements Command<List<ProfessionalGroupDto>> {

	private ProfessionalGroupRepository repo = Factories.repository.forProfessionalGroup();

	@Override
	public List<ProfessionalGroupDto> execute() throws BusinessException {
		return DtoAssembler.toDtoList(repo.findAll());
	}

}
