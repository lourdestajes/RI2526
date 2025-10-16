package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanicCommand;
import uo.ri.cws.application.service.mechanic.crud.commands.DeleteMechanicCommand;
import uo.ri.cws.application.service.mechanic.crud.commands.FindAllCommand;
import uo.ri.cws.application.service.mechanic.crud.commands.FindByIdCommand;
import uo.ri.cws.application.service.mechanic.crud.commands.FindByNifCommand;
import uo.ri.cws.application.service.mechanic.crud.commands.UpdateMechanicCommand;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {
	private CommandExecutor executor = new CommandExecutor();
	
	 
	public MechanicDto create(MechanicDto mechanic)
			throws BusinessException {
		return executor.execute( new AddMechanicCommand( mechanic ));
	}

	 
	public void delete(String idMechanic) throws BusinessException {
		executor.execute( new DeleteMechanicCommand( idMechanic ));
	}

	 
	public void update(MechanicDto mechanic) throws BusinessException {
		executor.execute( new UpdateMechanicCommand( mechanic ));
	}

	 
	public Optional<MechanicDto> findById(String id)
			throws BusinessException {
		return executor.execute( new FindByIdCommand( id ) );
	}

	 
	public Optional<MechanicDto> findByNif(String nif) throws BusinessException {
		return executor.execute( new FindByNifCommand( nif ) );
	}

	 
	public List<MechanicDto> findAll() throws BusinessException {
		return executor.execute( new FindAllCommand() );
	}

}
