package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

/**
 * Returns all payrolls (a summary) for a professional group
 * 
 * @param name the name of the professional group
 * @throws BussinessException       DOES NOT
 * @throws IllegalArgumentException when argument is null or empty.
 */
public class FindPayrollsSummarizedByProfessionalGroupName
        implements Command<List<PayrollSummaryDto>> {

    private String name;

    public FindPayrollsSummarizedByProfessionalGroupName(String name) {
        ArgumentChecks.isNotBlank(name, "Invalid null or blank name");
        this.name = name;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        checkExists(name);
        return DtoAssembler.toSummaryDtoList(Factories.repository.forPayroll()
            .findByProfessionalGroupName(name));
    }

    private void checkExists(String name) throws BusinessException {
        BusinessChecks.exists(Factories.repository.forProfessionalGroup()
            .findByName(name), "Professional group does not exist");

    }

}
