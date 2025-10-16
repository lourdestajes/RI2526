package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import java.util.UUID;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic {

    private MechanicGateway mechanicGateway = new MechanicGatewayImpl();
    private MechanicDto mechanic;

    public AddMechanic(MechanicDto mechanic) {
        ArgumentChecks.isNotNull(mechanic, "null argument is not allowed");
        ArgumentChecks.isNotBlank(mechanic.nif, "NIF cannot be null or blank");
        ArgumentChecks
                .isNotBlank(mechanic.name, "Name cannot be null or blank");
        ArgumentChecks.isNotBlank(
                mechanic.surname, "Surname cannot be null or blank");
        this.mechanic = mechanic;
        this.mechanic.id = UUID.randomUUID().toString();
        this.mechanic.version = 1L;
    }

    public MechanicDto execute() throws BusinessException {
        checkNifUnique(mechanic.nif);
        return addMechanic(mechanic);
    }

    private MechanicDto addMechanic(MechanicDto m) {
        // Process
        mechanicGateway.add(MechanicAssembler.toRecord(m));
        return mechanic;
    }

    private void checkNifUnique(String nif) throws BusinessException {
        Optional<MechanicRecord> existing = mechanicGateway.findByNif(nif);
        BusinessChecks.isFalse(
                existing.isPresent(),
                "There is already a mechanic with NIF " + nif);
    }

}
