package uo.ri.cws.application.service.contracttype.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.crud.command.AddContractType;
import uo.ri.cws.application.service.contracttype.crud.command.DeleteContractType;
import uo.ri.cws.application.service.contracttype.crud.command.FindAll;
import uo.ri.cws.application.service.contracttype.crud.command.FindByName;
import uo.ri.cws.application.service.contracttype.crud.command.UpdateContractType;
import uo.ri.util.exception.BusinessException;

public class ContractTypeCrudServiceImpl implements ContractTypeCrudService {

	private CommandExecutor executor = new CommandExecutor();
	
	 
	public ContractTypeDto create(ContractTypeDto dto) throws BusinessException {
		return executor.execute(new AddContractType(dto));
	}

	 
	public void delete(String id) throws BusinessException {
		executor.execute(new DeleteContractType(id));
	}

	 
	public void update(ContractTypeDto dto) throws BusinessException {
		executor.execute(new UpdateContractType(dto));
	}

	 
	public List<ContractTypeDto> findAll() throws BusinessException {
		return executor.execute(new FindAll());
	}

	 
	public Optional<ContractTypeDto> findByName(String name) throws BusinessException {
		return executor.execute(new FindByName(name));
	}

}
