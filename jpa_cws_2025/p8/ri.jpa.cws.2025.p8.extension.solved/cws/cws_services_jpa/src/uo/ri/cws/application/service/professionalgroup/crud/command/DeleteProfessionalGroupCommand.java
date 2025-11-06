package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

/**
 * Removes the given category
 * @param name of the category
 * @throws IllegalArgumentException when
 * 		- name is null or empty
 * @throws BusinessException if:
 * 		- the group does not exist, or
 * 		- the group has contracts assigned
 */
public class DeleteProfessionalGroupCommand implements Command<Void> {

	private String name;
	private ProfessionalGroupRepository repo = Factories.repository.forProfessionalGroup();
	
	public DeleteProfessionalGroupCommand(String name) {
		ArgumentChecks.isNotBlank(name, "Invalid contract type name");
		this.name = name;
	}

	@Override
	public Void execute() throws BusinessException {
		ProfessionalGroup group = checkTypeExists(name);
		canBeDeleted(group);
		repo.remove(group);
		return null;
	}

	private void canBeDeleted(ProfessionalGroup group) throws BusinessException {
		BusinessChecks.isTrue(group.getContracts().isEmpty());
	}

	private ProfessionalGroup checkTypeExists(String name) throws BusinessException {
		Optional<ProfessionalGroup> o = repo.findByName(name);
		BusinessChecks.exists(o, "Professional Group does not exist");
		return o.get();
	}

}
