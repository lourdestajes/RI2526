package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindMechanicsWithWageGreaterThan implements Command<List<MechanicDto>> {

	private double wage;

	public FindMechanicsWithWageGreaterThan(double amount) {
		ArgumentChecks.isTrue(amount >=0, "invalid wage");
		this.wage = amount;
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		List<MechanicRecord> records = Factories.persistence.forMechanic().findBySalaryGreater(wage);
		return new DtoAssembler().toDtoList(records);
	}

}
