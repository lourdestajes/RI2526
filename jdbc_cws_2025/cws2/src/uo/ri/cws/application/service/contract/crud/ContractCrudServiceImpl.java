package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.crud.command.AddContract;
import uo.ri.cws.application.service.contract.crud.command.DeleteContract;
import uo.ri.cws.application.service.contract.crud.command.FindAllContracts;
import uo.ri.cws.application.service.contract.crud.command.FindAllContractsByMechanicNif;
import uo.ri.cws.application.service.contract.crud.command.FindContractById;
import uo.ri.cws.application.service.contract.crud.command.FindInforceContracts;
import uo.ri.cws.application.service.contract.crud.command.TerminateContract;
import uo.ri.cws.application.service.contract.crud.command.UpdateContract;
import uo.ri.util.exception.BusinessException;

public class ContractCrudServiceImpl implements ContractCrudService {

	private CommandExecutor executor = new CommandExecutor();

	 
	public ContractDto create(ContractDto arg) throws BusinessException {
		return executor.execute(new AddContract(arg));

	}

	 
	public void update(ContractDto dto) throws BusinessException {
		executor.execute(new UpdateContract(dto));

	}

	 
	public void delete(String id) throws BusinessException {
		executor.execute(new DeleteContract(id));

	}

	 
	public void terminate(String contractId) throws BusinessException {
		executor.execute(new TerminateContract(contractId));

	}

	 
	public Optional<ContractDto> findById(String id) throws BusinessException {
		return executor.execute(new FindContractById(id));
	}

	 
	public List<ContractSummaryDto> findByMechanicNif(String mechanicNif) throws BusinessException {
		return executor.execute(new FindAllContractsByMechanicNif( mechanicNif ));

	}

	 
	public List<ContractSummaryDto> findAll() throws BusinessException {
		return executor.execute(new FindAllContracts());
	}

	 
	public List<ContractDto> findInforceContracts() throws BusinessException {
		return executor.execute(new FindInforceContracts());
	}

	
}
