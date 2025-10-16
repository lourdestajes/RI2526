package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class DeleteMechanicAction implements Action {

     
    public void execute() throws BusinessException {

        String idMechanic = Console.readString("Type mechanic id ");

        MechanicCrudService service = new MechanicCrudServiceImpl();
        service.delete(idMechanic);

        Console.println("Mechanic deleted");
    }

}