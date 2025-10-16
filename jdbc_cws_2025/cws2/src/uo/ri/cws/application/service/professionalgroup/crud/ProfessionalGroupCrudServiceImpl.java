package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService;
import uo.ri.cws.application.service.professionalgroup.crud.command.AddProfessionalGroup;
import uo.ri.cws.application.service.professionalgroup.crud.command.DeleteProfessionalGroup;
import uo.ri.cws.application.service.professionalgroup.crud.command.FindAllProfessionalGroups;
import uo.ri.cws.application.service.professionalgroup.crud.command.FindProfessionalGroupByName;
import uo.ri.cws.application.service.professionalgroup.crud.command.UpdateProfessionalGroup;
import uo.ri.util.exception.BusinessException;

public class ProfessionalGroupCrudServiceImpl implements ProfessionalGroupCrudService {

	private CommandExecutor executor = new CommandExecutor();// CommandExecutor();

	 
	public ProfessionalGroupDto create(ProfessionalGroupDto dto) throws BusinessException {
		return executor.execute( new AddProfessionalGroup (dto));
	}

	 
	public void delete(String id) throws BusinessException {
		executor.execute( new DeleteProfessionalGroup (id));
	}

	 
	public void update(ProfessionalGroupDto dto) throws BusinessException {
		executor.execute( new UpdateProfessionalGroup (dto));
	}

	 
	public List<ProfessionalGroupDto> findAll() throws BusinessException {
		return 		executor.execute( new FindAllProfessionalGroups ());


	}


	 
	public Optional<ProfessionalGroupDto> findByName(String arg) throws BusinessException {
		return executor.execute(new FindProfessionalGroupByName(arg));
	}


}
