package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ContractJpaRepository 
	extends BaseJpaRepository<Contract> 
	implements ContractRepository {

	@Override
	public List<Contract> findAllInForce() {
		return Jpa.getManager().createNamedQuery("Contract.findAllInForce", Contract.class)
				.getResultList();
	}

	@Override
	public List<Contract> findByMechanicId(String id) {
		return Jpa.getManager().createNamedQuery("Contract.findByMechanicId", Contract.class)
				.getResultList();
	}

	@Override
	public List<Contract> findByProfessionalGroupId(String id) {
		return Jpa.getManager().createNamedQuery("Contract.findByProfessionalGroupId", Contract.class)
				.getResultList();	
	}

	@Override
	public List<Contract> findByContractTypeId(String id) {
		return Jpa.getManager().createNamedQuery("Contract.findByContractTypeId", Contract.class)
				.getResultList();
	}

	@Override
	public List<Contract> findAllInForceThisMonth(LocalDate present) {
//		LocalDate startDate = LocalDate.now().withDayOfMonth(1);
//		LocalDate endDate = startDate.plusMonths(1).minusDays(1);
		
        LocalDate startDate = present.withDayOfMonth(1);
        LocalDate endDate = startDate.plusMonths(1)
            .minusDays(1);
		return Jpa.getManager().createNamedQuery("Contract.findAllInForceThisMonth", Contract.class)
				.setParameter(1, startDate)
				.setParameter(2, endDate)
				.getResultList();
	}

	@Override
	public List<Contract> findInforceContracts() {
		return Jpa.getManager().createNamedQuery("Contract.findAllInForce", Contract.class)
				.getResultList();
	}

}
