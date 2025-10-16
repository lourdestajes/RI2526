package uo.ri.cws.application.service.spare.crud;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.spare.crud.commands.AddSparePart;
import uo.ri.cws.application.service.spare.crud.commands.DeleteSparePart;
import uo.ri.cws.application.service.spare.crud.commands.FindSparePartByCode;
import uo.ri.cws.application.service.spare.crud.commands.UpdateSparePart;
import uo.ri.util.exception.BusinessException;

public class SparePartCrudServiceImpl implements SparePartCrudService {

	private CommandExecutor executor = new CommandExecutor();

	 
	public SparePartDto create(SparePartDto dto) throws BusinessException {
		return executor.execute( new AddSparePart(dto) );
	}

	 
	public void delete(String code) throws BusinessException {
		executor.execute( new DeleteSparePart(code) );
	}

	 
	public void update(SparePartDto dto) throws BusinessException {
		executor.execute( new UpdateSparePart(dto) );
	}

	 
	public Optional<SparePartDto> findByCode(String code) throws BusinessException {
		return executor.execute( new FindSparePartByCode(code) );
	}

}
