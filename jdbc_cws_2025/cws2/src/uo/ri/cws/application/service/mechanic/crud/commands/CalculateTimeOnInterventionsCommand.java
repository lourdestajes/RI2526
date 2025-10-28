package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class CalculateTimeOnInterventionsCommand implements Command<Integer> {

	private String mechanicId;
	private MechanicGateway mg = Factories.persistence.forMechanic();
	private InterventionGateway ig = Factories.persistence.forIntervention();
	
	public CalculateTimeOnInterventionsCommand(String mechanicId) {
		ArgumentChecks.isNotBlank(mechanicId, mechanicId);
		this.mechanicId = mechanicId;
	}
	
	@Override
	public Integer execute() throws BusinessException {
		checkMechanicExists();
		return ig.findByMechanicId(mechanicId).stream().mapToInt(i -> i.minutes).sum();
	}

	private void checkMechanicExists() throws BusinessException {
		Optional<MechanicRecord> optional = mg.findById(mechanicId);
		BusinessChecks.exists(optional, "The mechanic does not exist: " + mechanicId);
	}

}
