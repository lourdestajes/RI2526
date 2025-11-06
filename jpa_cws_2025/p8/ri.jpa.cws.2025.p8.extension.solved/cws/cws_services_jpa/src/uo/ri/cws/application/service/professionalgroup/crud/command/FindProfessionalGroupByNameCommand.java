package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

/**
 * @param name, of the contract type
 * @throws IllegalArgumentException if
 * 		- name is null or empty
 * @return the dto with all the fields set, or
 * 		empty if does not exist the contract type
 * @throws BusinessException DOES NOT
 */
public class FindProfessionalGroupByNameCommand implements Command<Optional<ProfessionalGroupDto>> {

	private String name;
	private ProfessionalGroupRepository repo = Factories.repository.forProfessionalGroup();


	public FindProfessionalGroupByNameCommand(String name) {
        ArgumentChecks.isNotBlank ( name, "Name cannot be blank" );
        this.name = name;
	}

	@Override
	public Optional<ProfessionalGroupDto> execute() throws BusinessException {
		return DtoAssembler.toOptionalDto( repo.findByName(name) );

	}

}
