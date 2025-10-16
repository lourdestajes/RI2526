package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.ListAllMechanics;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListAllMechanicsAction implements Action {


     
    public void execute() throws BusinessException {

        Console.println("\nList of mechanics \n");
        List<MechanicDto> mechanics = new ListAllMechanics().execute();
        printMechanics(mechanics);

    }

    private void printMechanics(List<MechanicDto> mechanics) {
       if (mechanics.isEmpty()) {
           Console.println("There are no mechanics");
       } else {
           Console.printf("\t%s \t%-10.10s \t%-15.15s \t%-25.25s\n",
                   "Id",
                   "NIF",
                   "Name",
                   "Surname");
           for (MechanicDto m : mechanics) {
               Console.printf("\t%s \t%-10.10s \t%-15.15s \t%-25.25s\n",
                       m.id,
                       m.nif,
                       m.name,
                       m.surname);
           }
       }
        
    }
}