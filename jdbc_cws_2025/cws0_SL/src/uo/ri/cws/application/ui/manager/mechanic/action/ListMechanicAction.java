package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.ListMechanicByNif;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {


     
    public void execute() throws BusinessException {

        // Get info
        String nif = Console.readString("nif");

        Optional<MechanicDto> om = new ListMechanicByNif(nif).execute();
        if (!om.isPresent()) {
            Console.println("There is no mechanic with that NIF");
            return;
        }
        MechanicDto rs = om.get();
        Console.println("\nMechanic information \n");

        Console.printf("\t%s %s %s %s %d\n",
                rs.id,
                rs.nif,
                rs.name,
                rs.surname,
                rs.version);
    }
}