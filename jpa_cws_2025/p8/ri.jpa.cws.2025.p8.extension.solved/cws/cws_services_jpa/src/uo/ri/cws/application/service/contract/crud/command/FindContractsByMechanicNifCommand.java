package uo.ri.cws.application.service.contract.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.service.contract.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindContractsByMechanicNifCommand implements Command<List<ContractSummaryDto>> {

	private String mechanicNif;
	private MechanicRepository mechanicRepo = Factories.repository.forMechanic();

	public FindContractsByMechanicNifCommand(String nif) {
		ArgumentChecks.isNotBlank(nif, "Invalid null or blank nif");
		mechanicNif = nif;	
	}

	@Override
	public List<ContractSummaryDto> execute() throws BusinessException {
		List<Contract> result = new ArrayList<>(); 
		Optional<Mechanic> mechanic = mechanicRepo.findByNif(mechanicNif);
		if (mechanic.isPresent()) {
			result.addAll(mechanic.get().getContracts());
			return DtoAssembler.toContractSummaryDtoList( result );
		}
		return List.of();
	}

}
