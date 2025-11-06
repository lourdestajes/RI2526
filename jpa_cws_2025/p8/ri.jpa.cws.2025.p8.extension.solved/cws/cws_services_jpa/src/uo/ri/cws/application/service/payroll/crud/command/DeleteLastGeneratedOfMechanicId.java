package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

/**
 * Deletes the payroll generated last month for the mechanic passed as argument
 *
 * @param mechanic identifier
 * @throws {@link BusinessException} if there are no mechanic with that id
 */
public class DeleteLastGeneratedOfMechanicId implements Command<Void> {

    private String id;
    private PayrollRepository repo = Factories.repository.forPayroll();
    private MechanicRepository mechRepo = Factories.repository.forMechanic();

    public DeleteLastGeneratedOfMechanicId(String mechanicId) {
        ArgumentChecks.isNotBlank(mechanicId, "Id cannot be null or blank");
        this.id = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
        checkExists(id);
        Optional<Payroll> o = repo.findLastPayrollByMechanicId(id);
        if (o.isPresent()) {
            repo.remove(o.get());
        }
        return null;
    }

    private void checkExists(String id) throws BusinessException {
        Optional<Mechanic> o = mechRepo.findById(id);
        BusinessChecks.exists(o, "Mechanic does no exist");
    }

}
