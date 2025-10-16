package uo.ri.cws.application.service.contract.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractSummaryDto;
import uo.ri.cws.application.service.contract.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindAllContractsByMechanicNif implements Command<List<ContractSummaryDto>> {
    private ContractGateway contractRepo = Factories.persistence.forContract();
	private MechanicGateway mechanicRepo = Factories.persistence.forMechanic();
	private PayrollGateway payrollRepo = Factories.persistence.forPayroll();
    private DtoAssembler assembler = new DtoAssembler();

	private String nif;

    public FindAllContractsByMechanicNif(String mechanicNif) {
    	ArgumentChecks.isNotNull(mechanicNif, "NIF cannot be null or empty to find contracts");
    	this.nif = mechanicNif;
	}

	 
    public List<ContractSummaryDto> execute() throws BusinessException {
		List<ContractSummaryDto> result = new ArrayList<>();
		
		Optional<MechanicRecord> m = mechanicRepo.findByNif(nif);
		if (! m.isEmpty()) {
			List<ContractRecord> contracts = contractRepo.findByMechanicId(m.get().id);
			result = summarizeContracts(contracts);
		}
		return result; 		 
    }

	private List<ContractSummaryDto> summarizeContracts(List<ContractRecord> contracts) {
		List<ContractSummaryDto> result = new ArrayList<>();
		result = assembler.toContractSummaryDtoList(contracts);
		fillNif(result);
		countPayrolls(result);
		return result;
	}

	private void countPayrolls(List<ContractSummaryDto> list) {
		for (ContractSummaryDto dto : list) {
			dto.numPayrolls = payrollRepo.findByContractId(dto.id).size();
		}		
	}

	private void fillNif(List<ContractSummaryDto> list) {
		for (ContractSummaryDto dto : list) {
			dto.nif = nif;
		}		
	}

}
