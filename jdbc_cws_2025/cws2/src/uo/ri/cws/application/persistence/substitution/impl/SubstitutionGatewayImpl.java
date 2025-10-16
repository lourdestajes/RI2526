package uo.ri.cws.application.persistence.substitution.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class SubstitutionGatewayImpl implements SubstitutionGateway {

	 
	public void add(SubstitutionRecord t) throws PersistenceException {

	}

	 
	public void remove(String id) throws PersistenceException {

	}

	 
	public void update(SubstitutionRecord t) throws PersistenceException {

	}

	 
	public Optional<SubstitutionRecord> findById(String id) throws PersistenceException {
		return null;
	}

	 
	public List<SubstitutionRecord> findAll() throws PersistenceException {
		return null;
	}

	 
	public List<SubstitutionRecord> findBySparePartId(String sparePartId) throws PersistenceException {
		return new JdbcTemplate<SubstitutionRecord>()
				.forNamedSql( "TSUBSTITUTIONS_FINDBYSPAREPARTID" )
				.withBinder(pst -> pst.setString(1, sparePartId))
				.withRowMapper(RecordAssembler::toSubstitutionRecord)
				.getResultList();
	}

}
