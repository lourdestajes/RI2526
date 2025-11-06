package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.exception.BusinessException;

/**
 * Returns all payrolls (a summary).
 *
 * @return List of all payrolls summary, or empty.
 * @throws {@link BusinessException}
 */
public class FindAllPayrollsSummarized
        implements Command<List<PayrollSummaryDto>> {

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        return DtoAssembler.toSummaryDtoList(Factories.repository.forPayroll()
            .findAll());
    }

}
