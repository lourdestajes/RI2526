package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;
    private MechanicRepository repo = Factories.repository.forMechanic ( );

    public DeleteMechanic ( String mechanicId ) {
        ArgumentChecks.isNotBlank ( mechanicId, "Id cannot be blank" );
        this.mechanicId = mechanicId;
    }

    public Void execute ( ) throws BusinessException {
        Mechanic m = findMechanic ( mechanicId );
        canBeDeleted ( m );
        repo.remove ( m );
        return null;
    }

    private void canBeDeleted ( Mechanic m ) throws BusinessException {
        BusinessChecks.isTrue ( m.getAssigned ( )
            .isEmpty ( ), "Mechanic has workOrders" );
        BusinessChecks.isTrue ( m.getInterventions ( )
            .isEmpty ( ), "Mechanic has interventions" );
        BusinessChecks.isTrue ( m.getContracts().isEmpty ( ), "Mechanic has interventions" );
    }

    private Mechanic findMechanic ( String id ) throws BusinessException {
        Optional<Mechanic> opt = repo.findById ( id );
        BusinessChecks.exists ( opt, "Mechanic does not exist" );
        return opt.get ( );
    }

}
