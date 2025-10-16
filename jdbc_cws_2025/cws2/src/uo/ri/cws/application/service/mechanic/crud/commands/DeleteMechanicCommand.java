package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanicCommand implements Command<Void> {
	private MechanicGateway mg = Factories.persistence.forMechanic();
	private ContractGateway cg = Factories.persistence.forContract();
	private InterventionGateway ig = Factories.persistence.forIntervention();
	private WorkOrderGateway wg = Factories.persistence.forWorkOrder();

	private String id;

	public DeleteMechanicCommand(String idMechanic) {
		ArgumentChecks.isNotNull(idMechanic);
		this.id = idMechanic;
	}

	 
	public Void execute() throws BusinessException {
		assertExists( id );
		assertHasNoDependencies( id );
		
		mg.remove(id);
		
		return null;
	}

	private void assertHasNoDependencies(String id) throws BusinessException {
		List<InterventionRecord> li = ig.findByMechanicId(id);
		BusinessChecks.isTrue(li.isEmpty(), "The mechanic has interventions");
		
		List<WorkOrderRecord> lw = wg.findByMechanicId(id);
		BusinessChecks.isTrue(lw.isEmpty(), "The mechanic has workorders assigned");
		
		List<ContractRecord> lr = cg.findByMechanicId(id);
		BusinessChecks.isTrue(lr.isEmpty(), "The mechanic has contracts and cannot be deleted");
	}

	private void assertExists(String id) throws BusinessException {
		Optional<MechanicRecord> om = mg.findById(id);
		BusinessChecks.exists(om, "The mechanic does not exist");
	}

}
