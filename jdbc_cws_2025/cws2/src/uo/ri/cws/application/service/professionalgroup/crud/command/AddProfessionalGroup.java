package uo.ri.cws.application.service.professionalgroup.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupCrudService.ProfessionalGroupDto;
import uo.ri.cws.application.service.professionalgroup.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProfessionalGroup implements Command<ProfessionalGroupDto> {

	private ProfessionalGroupDto dto = null;
	private ProfessionalGroupGateway repo = Factories.persistence.forProfessionalGroup();
	private DtoAssembler assembler = new DtoAssembler();
	
	public AddProfessionalGroup(ProfessionalGroupDto arg) {
		ArgumentChecks.isNotNull(arg, "argument cannot be null");
		ArgumentChecks.isNotBlank(arg.name, "name cannot be empty");
		ArgumentChecks.isTrue(arg.productivityRate>=0, "plus cannot be negative");
		ArgumentChecks.isTrue(arg.trienniumPayment>=0, "triennium cannot be negative");

		this.dto = arg;
	}

	 
	public ProfessionalGroupDto execute() throws BusinessException {
		Optional<ProfessionalGroupRecord> optionalRecord = repo
				.findByName(this.dto.name);
		BusinessChecks.doesNotExist( optionalRecord, "Trying to add a repeated professional group");

		ProfessionalGroupRecord record = assembler.toRecord(dto);
		repo.add(record);

		this.dto.id = record.id;
		this.dto.version = record.version;
		return this.dto; 
	}

}
