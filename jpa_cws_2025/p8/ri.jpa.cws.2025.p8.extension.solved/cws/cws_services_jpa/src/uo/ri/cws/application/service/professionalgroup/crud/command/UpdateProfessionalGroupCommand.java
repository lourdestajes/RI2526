package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;


/**
 * Updates only the productivityPlus and the trienniumSalary fields 
 * @param dto, only name, trienniumSalary and productivitySalary fields 
 * 	are useful for this operation, the rest will be ignored
 * @throws IllegalArgumentException when
 * 		- arg is null, or
 * 		- name is null or empty
 * 		- the new trienniumSalary is negative, or
 * 		- the new productivitySalary is negative
 * @throws BusinessException if:
 * 		- the group does not exist
 */
public class UpdateProfessionalGroupCommand implements Command<Void> {

	private ProfessionalGroupRepository repo = Factories.repository.forProfessionalGroup();
	private ProfessionalGroupDto dto;


	public UpdateProfessionalGroupCommand(ProfessionalGroupDto dto) {
		ArgumentChecks.isNotNull(dto, "Invalid null dto");
		ArgumentChecks.isNotBlank(dto.name, "Name cannot be null or blank");
		ArgumentChecks.isTrue(dto.trienniumPayment > 0, "Triennium cannot be negative");
		ArgumentChecks.isTrue(dto.productivityRate> 0, "Productivity cannot be negative");
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		ProfessionalGroup group = checkTypeExists(dto.name);
		BusinessChecks.hasVersion(dto.version, group.getVersion());
		group.setTrienniumPayment(dto.trienniumPayment);
		group.setProductivityRate(dto.productivityRate);
		group.updatedNow();
		return null;
	}
	private ProfessionalGroup checkTypeExists(String name) throws BusinessException {
		Optional<ProfessionalGroup> o = repo.findByName(name);
		BusinessChecks.exists(o, "ProfessionalGroup does not exist");
		return o.get();
	}
}

