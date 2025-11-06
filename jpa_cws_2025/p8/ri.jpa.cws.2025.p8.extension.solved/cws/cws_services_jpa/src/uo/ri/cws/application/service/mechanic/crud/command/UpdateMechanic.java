package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicRepository repo = Factories.repository.forMechanic ( );

    public UpdateMechanic ( MechanicDto dto ) {
        ArgumentChecks.isNotNull ( dto, "Cannot add a null mechanic" );
        ArgumentChecks.isNotBlank ( dto.id, "Id cannot be blank" );
        ArgumentChecks.isNotBlank ( dto.nif, "Nif cannot be blank" );
        ArgumentChecks.isNotBlank ( dto.name, "Name cannot be blank" );
        ArgumentChecks.isNotBlank ( dto.surname, "Surname cannot be blank" );
        this.dto = dto;
    }

    public Void execute ( ) throws BusinessException {
        Mechanic m = findMechanic ( dto.id );
        BusinessChecks.hasVersion(m.getVersion(), dto.version);
        m.setName ( dto.name );
        m.setSurname ( dto.surname );
        m.updatedNow();
        return null;
    }

    private Mechanic findMechanic ( String id ) throws BusinessException {
        Optional<Mechanic> opt = repo.findById ( id );
        BusinessChecks.exists ( opt, "Mechanic does not exist" );
        return opt.get ( );
    }
}
