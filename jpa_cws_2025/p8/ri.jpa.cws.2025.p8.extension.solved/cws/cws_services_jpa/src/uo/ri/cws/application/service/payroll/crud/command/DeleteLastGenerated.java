package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.exception.BusinessException;

/**
 * Deletes all payrolls generated for last month.
 * 
 * @return number of payrolls deleted
 * @throws DOES NOT BusinessException
 */

public class DeleteLastGenerated
        implements Command<Integer> {

    private PayrollRepository repo = Factories.repository.forPayroll();

    @Override
    public Integer execute() throws BusinessException {
        List<Payroll> payrolls = repo.findLastMonthPayrolls();
        payrolls.stream()
            .forEach(repo::remove);
        return payrolls.size();
    }

}
