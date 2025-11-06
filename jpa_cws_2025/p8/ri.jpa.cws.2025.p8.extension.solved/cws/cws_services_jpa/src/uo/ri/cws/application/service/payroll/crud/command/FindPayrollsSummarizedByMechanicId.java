package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

/**
 * Returns all payrolls (a summary) for a certain mechanic.
 *
 * @param id mechanic id
 * @return List of payrolls summary, or empty.
 * @throws IllegalArgument   Exception when argument is null or empty
 * @throws BusinessException when mechanic does not exist
 */
public class FindPayrollsSummarizedByMechanicId
        implements Command<List<PayrollSummaryDto>> {

    private String id;
    private MechanicRepository mechRepo = Factories.repository.forMechanic();

    public FindPayrollsSummarizedByMechanicId(String id) {
        ArgumentChecks.isNotBlank(id, "Invalid null or blank id");
        this.id = id;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        checkExists(id);
        return DtoAssembler.toSummaryDtoList(Factories.repository.forPayroll()
            .findByMechanicId(id));
    }

    private void checkExists(String id) throws BusinessException {
        Optional<Mechanic> o = mechRepo.findById(id);
        BusinessChecks.exists(o, "Mechanic does no exist");
    }

}
