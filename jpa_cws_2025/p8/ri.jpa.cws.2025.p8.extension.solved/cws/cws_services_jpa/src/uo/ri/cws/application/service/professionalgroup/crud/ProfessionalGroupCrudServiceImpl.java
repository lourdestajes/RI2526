package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.cws.application.service.professionalgroup.crud.command.CreateProfessionalGroupCommand;
import uo.ri.cws.application.service.professionalgroup.crud.command.DeleteProfessionalGroupCommand;
import uo.ri.cws.application.service.professionalgroup.crud.command.FindAllProfessionalGroupsCommand;
import uo.ri.cws.application.service.professionalgroup.crud.command.FindProfessionalGroupByNameCommand;
import uo.ri.cws.application.service.professionalgroup.crud.command.UpdateProfessionalGroupCommand;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ProfessionalGroupCrudServiceImpl implements ProfessionalGroupCrudService {

	private CommandExecutor executor = Factories.executor.forExecutor();

	@Override
	public ProfessionalGroupDto create(ProfessionalGroupDto dto) throws BusinessException {
		return executor.execute(new CreateProfessionalGroupCommand(dto));
	}

	@Override
	public void delete(String name) throws BusinessException {
		executor.execute(new DeleteProfessionalGroupCommand(name));
	}

	@Override
	public void update(ProfessionalGroupDto dto) throws BusinessException {
		executor.execute(new UpdateProfessionalGroupCommand(dto));
	}

	@Override
	public Optional<ProfessionalGroupDto> findByName(String name) throws BusinessException {
		return executor.execute(new FindProfessionalGroupByNameCommand(name));
	}

	@Override
	public List<ProfessionalGroupDto> findAll() throws BusinessException {
		return executor.execute(new FindAllProfessionalGroupsCommand());

	}

}
