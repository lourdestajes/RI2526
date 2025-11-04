package uo.ri.cws.application.service.contracttype.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService;
import uo.ri.cws.application.service.contracttype.crud.command.CreateContractTypeCommand;
import uo.ri.cws.application.service.contracttype.crud.command.DeleteContractTypeCommand;
import uo.ri.cws.application.service.contracttype.crud.command.FindAllContractTypesCommand;
import uo.ri.cws.application.service.contracttype.crud.command.FindContractTypeByNameCommand;
import uo.ri.cws.application.service.contracttype.crud.command.UpdateContractTypeCommand;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ContractTypeCrudServiceImpl implements ContractTypeCrudService {

	private CommandExecutor executor = Factories.executor.forExecutor();

	@Override
	public ContractTypeDto create(ContractTypeDto dto) throws BusinessException {
		return executor.execute(new CreateContractTypeCommand(dto));
	}

	@Override
	public void delete(String name) throws BusinessException {
		executor.execute(new DeleteContractTypeCommand(name));
	}

	@Override
	public void update(ContractTypeDto dto) throws BusinessException {
		executor.execute(new UpdateContractTypeCommand(dto));
	}

	@Override
	public Optional<ContractTypeDto> findByName(String name) throws BusinessException {
		return executor.execute(new FindContractTypeByNameCommand(name));
	}

	@Override
	public List<ContractTypeDto> findAll() throws BusinessException {
		return executor.execute(new FindAllContractTypesCommand());
	}

}
