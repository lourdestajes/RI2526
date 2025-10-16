package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicAssembler;

public class ListAllMechanics {

    public List<MechanicDto> execute() {
        List<MechanicRecord> records = new MechanicGatewayImpl().findAll();
        return MechanicAssembler.toDto(records);
    }
}