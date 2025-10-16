package uo.ri.cws.application.service.contract.crud.command;

import java.util.LinkedList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.service.contract.crud.DtoAssembler;

public class FindAllContracts implements Command<List<ContractSummaryDto>> {
	private ContractGateway repo = Factories.persistence.forContract();
	private MechanicGateway mg = Factories.persistence.forMechanic(); 
	private DtoAssembler assembler = new DtoAssembler();

	 
	public List<ContractSummaryDto> execute() {
		List<ContractRecord> contracts = repo.findAll();
		
		List<ContractSummaryDto> summaries = new LinkedList<ContractSummaryDto>();
		for (ContractRecord contract : contracts) {
			ContractSummaryDto summary = assembler.toContractSummaryDto(contract);
			summary.nif = mg.findById(contract.mechanicId).get().nif;
			summaries.add(summary);
		}
		
		return summaries;
	}

}
