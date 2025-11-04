package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto dto;
    private MechanicRepository repo = Factories.repository.forMechanic ( );

    public AddMechanic ( MechanicDto dto ) {
        ArgumentChecks.isNotNull ( dto, "Cannot add a null mechanic" );
        ArgumentChecks.isNotBlank ( dto.nif, "NIF cannot be blank" );
        ArgumentChecks.isNotBlank ( dto.name, "Name cannot be blank" );
        ArgumentChecks.isNotBlank ( dto.surname, "Surname cannot be blank" );
        this.dto = dto;
    }

    public MechanicDto execute ( ) throws BusinessException {
        checkDoesNotExist ( dto.nif );
        Mechanic m = new Mechanic ( dto.nif, dto.name, dto.surname );
        repo.add ( m );
        dto.id = m.getId ( );
        dto.version = m.getVersion ( );
        return dto;
    }

    private void checkDoesNotExist ( String nif ) throws BusinessException {
        BusinessChecks.doesNotExist ( repo.findByNif ( nif ),
                "A repeated nif cannot be added" );
    }

}
