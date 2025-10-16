package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    private MechanicCrudService service = new MechanicCrudServiceImpl();

     
    public void execute() throws BusinessException {

        // Get info
        String id = Console.readString("Type mechahic id to update");

        // check mechanic exists
        MechanicDto mechanic = checkMechanicExists(id);

        // Ask for new data
        // nif is the identity, cannot be changed
        mechanic.name = Console.readString("Name");
        mechanic.surname = Console.readString("Surname");

        // update
        service.update(mechanic);

        // Print result
        Console.println("Mechanic updated");
    }

    private MechanicDto checkMechanicExists(String id) throws BusinessException {
        Optional<MechanicDto> result = service.findById(id);
        if (result.isEmpty()) {
            throw new BusinessException("Mechanic does not exist");
        } 
        return result.get();
    }
}