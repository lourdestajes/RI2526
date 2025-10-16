package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListMechanicAction implements Action {


     
    public void execute() throws BusinessException {

        MechanicCrudService service = Factories.service
                .forMechanicCrudService();

        // Get info
        String nif = Console.readString("nif");

        Optional<MechanicDto> om = service.findByNif(nif);
        if (!om.isPresent()) {
            Console.println("There is no mechanic with that NIF");
            return;
        }
        MechanicDto rs = om.get();
        Console.println("\nMechanic information \n");
        Console.printf("\t%s \t%-10.10s \t%-15.15s \t%-25.25s\n",
                "Id",
                "NIF",
                "Name",
                "Surname");
        Printer.printMechanic(rs);
    }
}