package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

/**
 * Returns one payroll details.
 *
 * @param id payroll id
 * @return payroll dto
 * @throws IllegalArgumentException when argument is null or empty
 */
public class FindPayrollById
        implements Command<Optional<PayrollDto>> {

    private String id;

    public FindPayrollById(String id) {
        ArgumentChecks.isNotBlank(id, "Invalid null or blank id");
        this.id = id;
    }

    @Override
    public Optional<PayrollDto> execute() throws BusinessException {

        return DtoAssembler.toOptionalDto(Factories.repository.forPayroll()
            .findById(id));
    }

}
