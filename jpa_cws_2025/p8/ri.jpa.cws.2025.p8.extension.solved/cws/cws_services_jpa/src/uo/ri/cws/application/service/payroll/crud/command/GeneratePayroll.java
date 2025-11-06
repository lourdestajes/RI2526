package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.date.Dates;
import uo.ri.util.exception.BusinessException;

public class GeneratePayroll implements Command<List<PayrollDto>> {

    private PayrollRepository repo = Factories.repository.forPayroll();
    private ContractRepository contractRepo = Factories.repository
        .forContract();
    private LocalDate dateForPayroll;

    public GeneratePayroll(LocalDate date) {
        ArgumentChecks.isNotNull(date,
            "Generate payroll for month : argument cannot be null");
        this.dateForPayroll = Dates.lastDayOfMonth(date.minusMonths(1));

    }

    public GeneratePayroll() {
        this(LocalDate.now());
    }
    @Override
    public List<PayrollDto> execute() throws BusinessException {
        List<Contract> inForce = contractRepo
            .findAllInForceThisMonth(dateForPayroll)
            .stream()
            .filter(c -> payrollNotGeneratedForMonth(c, dateForPayroll))
            .toList();

        List<Payroll> payrolls = inForce.stream()
            .map(c -> new Payroll(c, dateForPayroll))
            .toList();
        payrolls.stream()
            .forEach(repo::add);
        return DtoAssembler.toDtoList(payrolls);
    }

    public boolean payrollNotGeneratedForMonth(Contract c, LocalDate date) {
        return c.getPayrolls()
            .stream()
            .filter(p -> p.getDate()
                .equals(date))
            .toList()
            .isEmpty();
    }
}
