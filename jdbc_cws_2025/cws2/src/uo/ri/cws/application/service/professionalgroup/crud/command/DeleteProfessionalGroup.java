package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteProfessionalGroup implements Command<Void> {

	private String name2Del = null;
	private ProfessionalGroupGateway profGroupG = Factories.persistence.forProfessionalGroup();
	private ContractGateway contractG = Factories.persistence.forContract();
	
	public DeleteProfessionalGroup(String arg) {
		ArgumentChecks.isNotNull(arg, "argument cannot be null");
		ArgumentChecks.isNotBlank(arg, "arg cannot be empty");
		
		this.name2Del = arg;
	}

	 
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroupRecord> optional = profGroupG.findByName(name2Del);
		BusinessChecks.exists(optional, "Trying to del a non existent professional group");
		ProfessionalGroupRecord group = optional.get();
		BusinessChecks.isTrue(contractG.findByProfessionalGroupId(group.id).isEmpty(), 
				"Trying to del a professional group with contracts");
		
		profGroupG.remove(group.id);
		return null;
	}

}
