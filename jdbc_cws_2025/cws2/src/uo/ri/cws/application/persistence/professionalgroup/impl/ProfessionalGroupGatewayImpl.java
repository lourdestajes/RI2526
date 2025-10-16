package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

	private ProfessionalGroupAssembler assembler = new ProfessionalGroupAssembler();

	 
	public void add(ProfessionalGroupRecord t) throws PersistenceException {
		new JdbcTemplate<ContractTypeRecord>()
		.forNamedSql( "TPROFESSIONALGROUPS_ADD" )
		.withBinder( pst -> {
            pst.setString(1, t.id);
            pst.setString(2, t.name);
            pst.setDouble(3, t.trienniumPayment);
            pst.setDouble(4, t.productivityRate);
            pst.setTimestamp(5, Timestamp.valueOf( t.createdAt ));
            pst.setTimestamp(6, Timestamp.valueOf( t.updatedAt ));
        })
		.execute();		
	}

	 
	public void remove(String id) throws PersistenceException {
		new JdbcTemplate<ProfessionalGroupRecord>()
		.forNamedSql( "TPROFESSIONALGROUPS_REMOVE" )
		.withBinder(pst -> pst.setString(1, id))
		.execute();		
	}

	 
	public void update(ProfessionalGroupRecord t) throws PersistenceException {
		new JdbcTemplate<ContractTypeRecord>()
		.forNamedSql( "TPROFESSIONALGROUPS_UPDATE" )
		.withBinder(pst -> {
			pst.setDouble(1, t.trienniumPayment);
			pst.setDouble(2, t.productivityRate);
			pst.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			pst.setString(4, t.id);
		})
		.execute();		
	}

	 
	public Optional<ProfessionalGroupRecord> findById(String id) throws PersistenceException {
		return new JdbcTemplate<ProfessionalGroupRecord>()
				.forNamedSql("TPROFESSIONALGROUPS_FINDBYID")
				.withBinder(pst -> pst.setString(1, id))
				.withRowMapper(rs -> assembler.toRecord(rs))
				.getOptionalResult();
	}

	 
	public Optional<ProfessionalGroupRecord> findByName(String name) {
		return new JdbcTemplate<ProfessionalGroupRecord>()
				.forNamedSql("TPROFESSIONALGROUPS_FINDBYNAME")
				.withBinder(pst -> pst.setString(1, name))
				.withRowMapper(rs -> assembler.toRecord(rs))
				.getOptionalResult();

	}

	 
	public List<ProfessionalGroupRecord> findAll() {
		return new JdbcTemplate<ProfessionalGroupRecord>()
				.forNamedSql( "TPROFESSIONALGROUPS_FINDALL" )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

}
