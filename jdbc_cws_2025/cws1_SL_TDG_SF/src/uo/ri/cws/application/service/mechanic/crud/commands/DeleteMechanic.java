package uo.ri.cws.application.service.mechanic.crud.commands;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic {

    private String idMechanic;
    private MechanicGateway mGat = Factories.persistence.forMechanic();
    private WorkOrderGateway woGat = Factories.persistence.forWorkOrder();
    private InterventionGateway iGat = Factories.persistence.forIntervention();

    public DeleteMechanic(String idMechanic) {
        ArgumentChecks.isNotEmpty(
                idMechanic, "The mechanic id cant be null or empty");
        this.idMechanic = idMechanic;
    }

    public void execute() throws BusinessException {

        checkIfMechanicExists(idMechanic);
        checksIfMechanicCanBeDeleted(idMechanic);
        deleteMechanic(idMechanic);

    }

    private void deleteMechanic(String id) {
        // Process
        mGat.remove(id);
    }

    private void checksIfMechanicCanBeDeleted(String id)
            throws BusinessException {
        checkIfMechanicHasWorkOrders(id);
        chekkIfMechanicHasInterventions(id);
    }

    private void chekkIfMechanicHasInterventions(String id)
            throws BusinessException {
        BusinessChecks.isTrue(
                iGat.findByMechanicId(id).isEmpty(),
                "The mechanic with id " + idMechanic
                        + " cannot be deleted because it has associated work orders");
    }

    private void checkIfMechanicHasWorkOrders(String id)
            throws BusinessException {
        BusinessChecks.isTrue(
                woGat.findByMechanicId(id).isEmpty(),
                "The mechanic with id " + idMechanic
                        + " cannot be deleted because it has associated work orders");
    }

    private void checkIfMechanicExists(String id) throws BusinessException {
        BusinessChecks.isTrue(
                mGat.findById(id).isPresent(),
                "The mechanic with id " + id + " does not exist");

    }

}