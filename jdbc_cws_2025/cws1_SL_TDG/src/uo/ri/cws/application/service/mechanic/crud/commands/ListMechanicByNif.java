package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class ListMechanicByNif {

    private String nif;

    public ListMechanicByNif(String nif) {
        ArgumentChecks.isNotEmpty(nif, "Mechanic NIF cannot be null or empty");
        this.nif = nif;
    }

    public Optional<MechanicDto> execute() {
        Optional<MechanicRecord> result = new MechanicGatewayImpl().findByNif(nif);
        return MechanicAssembler.toDto(result);
    }
}