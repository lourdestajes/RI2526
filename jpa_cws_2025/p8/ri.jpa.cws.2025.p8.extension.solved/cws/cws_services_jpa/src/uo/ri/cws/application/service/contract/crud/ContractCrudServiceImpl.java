package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.crud.command.CreateContractCommand;
import uo.ri.cws.application.service.contract.crud.command.DeleteContractCommand;
import uo.ri.cws.application.service.contract.crud.command.FindAllContractsCommand;
import uo.ri.cws.application.service.contract.crud.command.FindContractByIdCommand;
import uo.ri.cws.application.service.contract.crud.command.FindContractsByMechanicNifCommand;
import uo.ri.cws.application.service.contract.crud.command.FindInforceContractsCommand;
import uo.ri.cws.application.service.contract.crud.command.TerminateContractCommand;
import uo.ri.cws.application.service.contract.crud.command.UpdateContractCommand;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ContractCrudServiceImpl implements ContractCrudService {

	private CommandExecutor executor = Factories.executor.forExecutor();
	@Override
	public ContractDto create(ContractDto c) throws BusinessException {
		
		return executor.execute(new CreateContractCommand(c));
	}

	@Override
	public void update(ContractDto dto) throws BusinessException {
		executor.execute(new UpdateContractCommand(dto));

	}

	@Override
	public void delete(String id) throws BusinessException {
		executor.execute(new DeleteContractCommand(id));

	}

	@Override
	public void terminate(String contractId) throws BusinessException {
        executor.execute(new TerminateContractCommand(contractId));

	}

	@Override
	public Optional<ContractDto> findById(String id) throws BusinessException {
		return executor.execute(new FindContractByIdCommand(id));
	}

	@Override
	public List<ContractSummaryDto> findByMechanicNif(String nif) throws BusinessException {
		return executor.execute(new FindContractsByMechanicNifCommand(nif));
	}

	@Override
	public List<ContractDto> findInforceContracts() throws BusinessException {
		return executor.execute(new FindInforceContractsCommand());
	}

	@Override
	public List<ContractSummaryDto> findAll() throws BusinessException {
		return executor.execute(new FindAllContractsCommand());
	}

}
