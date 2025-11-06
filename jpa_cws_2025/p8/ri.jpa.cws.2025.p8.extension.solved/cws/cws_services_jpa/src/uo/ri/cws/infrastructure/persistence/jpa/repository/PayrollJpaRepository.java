package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
        implements PayrollRepository {

    @Override
    public List<Payroll> findByContract(String contractId) {
        return Jpa.getManager()
            .createNamedQuery("Payroll.findByContractId", Payroll.class)
            .setParameter(1, contractId)
            .getResultList();
    }

    @Override
    public List<Payroll> findLastMonthPayrolls() {
        LocalDate date = LocalDate.now()
            .withDayOfMonth(1);
        LocalDate firstDayOfLastMonth = date.minusMonths(1);
        LocalDate lastDayOfLastMonth = date.minusDays(1);
        return Jpa.getManager()
            .createNamedQuery("Payrolls.getAllInMonth", Payroll.class)
            .setParameter(1, firstDayOfLastMonth)
            .setParameter(2, lastDayOfLastMonth)
            .getResultList();
    }

    @Override
    public Optional<Payroll> findLastPayrollByMechanicId(String mechanicId) {
        LocalDate date = LocalDate.now()
            .withDayOfMonth(1);
        LocalDate firstDayOfLastMonth = date.minusMonths(1);
        LocalDate lastDayOfLastMonth = date.minusDays(1);
        return Jpa.getManager()
            .createNamedQuery("Payrolls.getLastMonthByMechanicId",
                Payroll.class)
            .setParameter(1, firstDayOfLastMonth)
            .setParameter(2, lastDayOfLastMonth)
            .setParameter(3, mechanicId)
            .getResultStream()
            .findFirst();
    }

    @Override
    public List<Payroll> findByProfessionalGroupName(String name) {
        return Jpa.getManager()
            .createNamedQuery("Payrolls.getByProfGroupName", Payroll.class)
            .setParameter(1, name)
            .getResultList();
    }

    @Override
    public List<Payroll> findByMechanicId(String mId) {
        return Jpa.getManager()
            .createNamedQuery("Payrolls.getByMechanicId", Payroll.class)
            .setParameter(1, mId)
            .getResultList();
    }

    @Override
    public Optional<Payroll> findByContractIdAndDate(String id,
            LocalDate date) {
        return Jpa.getManager()
            .createNamedQuery("Payrolls.getByContractIdAndDate", Payroll.class)
            .setParameter(1, id)
            .setParameter(2, date)
            .getResultStream()
            .findFirst();
    }

}
