package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ProfessionalGroupJpaRepository 
		extends BaseJpaRepository<ProfessionalGroup>
		implements ProfessionalGroupRepository {

	@Override
	public Optional<ProfessionalGroup> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


}
