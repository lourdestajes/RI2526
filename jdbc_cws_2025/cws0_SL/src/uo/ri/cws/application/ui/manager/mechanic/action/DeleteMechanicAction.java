package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.cws.application.service.mechanic.crud.DeleteMechanic;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteMechanicAction implements Action {

     
    public void execute() throws BusinessException {

        String idMechanic = Console.readString("Type mechanic id ");

        new DeleteMechanic(idMechanic).execute();

        Console.println("Mechanic deleted");
    }

}