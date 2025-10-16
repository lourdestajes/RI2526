package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProfessionalGroup implements Command<Void> {

	private ProfessionalGroupDto dto = null;
	private ProfessionalGroupGateway repo = Factories.persistence.forProfessionalGroup();

	public UpdateProfessionalGroup(ProfessionalGroupDto arg) {
		ArgumentChecks.isNotNull(arg, "argument cannot be null");
		ArgumentChecks.isNotNull(arg.name, "name cannot be empty");
		ArgumentChecks.isNotBlank(arg.name, "name cannot be empty");
		ArgumentChecks.isTrue(arg.productivityRate>=0, "plus cannot be negative");
		ArgumentChecks.isTrue(arg.trienniumPayment>=0, "triennium cannot be negative");

		this.dto = arg;
	}

	 
	public Void execute() throws BusinessException {
		Optional<ProfessionalGroupRecord> optional =
				repo.findByName(dto.name);
		BusinessChecks.exists(optional,
				"Trying to update a non existent professional group");
		ProfessionalGroupRecord group = optional.get();
		BusinessChecks.hasVersion(group.version, dto.version);

		group.productivityRate = dto.productivityRate;
		group.trienniumPayment = dto.trienniumPayment;
		
		repo.update(group);
		return null;
	}

}
