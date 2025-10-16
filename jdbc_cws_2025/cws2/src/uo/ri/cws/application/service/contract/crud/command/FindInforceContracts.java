package uo.ri.cws.application.service.contract.crud.command;

import java.util.LinkedList;
import java.util.List;

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
import uo.ri.util.exception.BusinessException;

public class FindInforceContracts implements Command<List<ContractDto>> {

	private ContractGateway repo = Factories.persistence.forContract();
	private MechanicGateway mg = Factories.persistence.forMechanic();
	private ContractTypeGateway ctg = Factories.persistence.forContractType();
	private ProfessionalGroupGateway pgm = Factories.persistence.forProfessionalGroup();

	 
	public List<ContractDto> execute() throws BusinessException {
		List<ContractRecord> contracts = repo.findAllInForce();
		DtoAssembler assembler = new DtoAssembler();
		
		List<ContractDto> res = new LinkedList<ContractDto>();
		for (ContractRecord c : contracts) {
			MechanicRecord m = mg.findById(c.mechanicId).get();
			ContractTypeRecord ct = ctg.findById(c.contractTypeId).get();
			ProfessionalGroupRecord pg = pgm.findById(c.professionalGroupId).get();
			
			ContractDto dto = assembler.toFullDto(c, m, ct, pg);
			res.add( dto );
		}
		
		return res;
	}

}
