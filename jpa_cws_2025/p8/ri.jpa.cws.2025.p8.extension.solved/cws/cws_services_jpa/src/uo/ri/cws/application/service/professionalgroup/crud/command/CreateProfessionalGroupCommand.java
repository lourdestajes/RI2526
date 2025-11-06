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
 * Add a new professional group with the given data
 * 
 * @param dto, the id value, if any, will be ignored
 * @return a dto filled with id and version
 * @throws IllegalArgumentException when
 * 		- dto is null, or
 * 		- name is null or empty, or
 * 		- triennium salary is negative, or
 * 		- productivity plus is negative
 * @throws BusinessException if:
 * 		- another category with the same name already exists
 */
public class CreateProfessionalGroupCommand implements Command<ProfessionalGroupDto> {

	private ProfessionalGroupDto dto;
	private ProfessionalGroupRepository repo = Factories.repository.forProfessionalGroup();

	public CreateProfessionalGroupCommand(ProfessionalGroupDto dto) {
        ArgumentChecks.isNotNull ( dto, "Cannot add a null contract type" );
        ArgumentChecks.isNotBlank ( dto.name, "Name cannot be blank" );
        ArgumentChecks.isTrue(dto.trienniumPayment>0, "Triennium pay cannot be negative");
        ArgumentChecks.isTrue(dto.productivityRate>0, "Productivity cannot be negative");
        this.dto = dto;
	}

	@Override
	public ProfessionalGroupDto execute() throws BusinessException {
		checkProfessionalGroupDoesNotExist(dto.name);
		ProfessionalGroup group = new ProfessionalGroup(dto.name, dto.trienniumPayment, dto.productivityRate);
		repo.add(group);
		dto.id = group.getId();
		dto.version = group.getVersion();
		return dto;
	}

	private void checkProfessionalGroupDoesNotExist(String name) throws BusinessException {
		Optional<ProfessionalGroup> opt = repo.findByName(name);
		BusinessChecks.doesNotExist(opt, "There’s already another professional group with the same name.");
	}

}
