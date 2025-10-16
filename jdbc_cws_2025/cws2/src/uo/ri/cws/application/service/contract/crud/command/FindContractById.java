package uo.ri.cws.application.service.contract.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindContractById implements Command<Optional<ContractDto>> {
	private String id;

    private ContractGateway repo = Factories.persistence.forContract();
	private MechanicGateway mg = Factories.persistence.forMechanic();
	private ContractTypeGateway ctg = Factories.persistence.forContractType();
	private ProfessionalGroupGateway pgm = Factories.persistence.forProfessionalGroup();

    private DtoAssembler assembler = new DtoAssembler();

    public FindContractById(String id) {
	
    	ArgumentChecks.isNotBlank(id);
    	this.id = id;
    }

     
    public Optional<ContractDto> execute() throws BusinessException {
    	Optional<ContractRecord> opt = repo.findById(id);
    	if (opt.isEmpty()) {
			return Optional.empty();
		}
    	ContractRecord c = opt.get();
    	MechanicRecord m = mg.findById(c.mechanicId).get();
    	ContractTypeRecord ct = ctg.findById(c.contractTypeId).get();
    	ProfessionalGroupRecord pg = pgm.findById(c.professionalGroupId).get();
    	
    	return opt.map(cc -> assembler.toFullDto(cc, m, ct, pg));
    }

}
