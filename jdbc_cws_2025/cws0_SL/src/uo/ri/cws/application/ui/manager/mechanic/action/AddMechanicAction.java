package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.AddMechanic;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class AddMechanicAction implements Action {

     
    public void execute() throws BusinessException {

        MechanicDto mechanic = new MechanicDto();
        // Get info
        mechanic.nif = Console.readString("nif");
        mechanic.name = Console.readString("Name");
        mechanic.surname = Console.readString("Surname");

        // Process

        new AddMechanic(mechanic).execute();

        // Print result
        Console.println("Mechanic added");
    }

}
