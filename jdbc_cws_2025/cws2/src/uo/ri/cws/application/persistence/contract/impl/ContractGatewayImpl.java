package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class ContractGatewayImpl implements ContractGateway {

	private RecordAssembler assembler = new RecordAssembler();
	
	 
	public void add(ContractRecord t) throws PersistenceException {
		new JdbcTemplate<ContractRecord>()
		.forNamedSql( "TCONTRACTS_ADD" )
		.withBinder( pst -> {
            pst.setString(1, t.id);
            pst.setDate(2, Date.valueOf(t.startDate));
            if (t.endDate != null) {
                pst.setDate(3, Date.valueOf(t.endDate));
            } else {
                pst.setNull(3, java.sql.Types.DATE);
            }
            pst.setString(4, t.mechanicId);
            pst.setString(5, t.professionalGroupId);
            pst.setString(6, t.contractTypeId);
            pst.setDouble(7, t.annualBaseSalary);
            pst.setDouble(8, t.settlement);
            pst.setDouble(9, t.taxRate);
            pst.setString(10, t.state);
            pst.setTimestamp(11, Timestamp.valueOf(t.createdAt));
            pst.setTimestamp(12, Timestamp.valueOf(t.updatedAt));    

        })
		.execute();
	}

	 
	public void remove(String id) throws PersistenceException {
		new JdbcTemplate<ContractRecord>()
		.forNamedSql( "TCONTRACTS_REMOVE" )
		.withBinder(pst -> pst.setString(1, id))
		.execute();
	}

	 
	public void update(ContractRecord t) throws PersistenceException {
		new JdbcTemplate<ContractRecord>()
		.forNamedSql( "TCONTRACTS_UPDATE" )
		.withBinder(pst -> {
			pst.setDouble(1, t.annualBaseSalary);
			if (t.endDate != null) {
	            pst.setDate(2, Date.valueOf(t.endDate));				
			}
			else {
	            pst.setNull(2, java.sql.Types.DATE);
			}
            pst.setDouble(3, t.settlement);
            pst.setDouble(4, t.taxRate);
            pst.setString(5, t.state);
            pst.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));    
			pst.setString(7, t.id);
			}
		)
		.execute();
	}
	
	

	 
	public Optional<ContractRecord> findById(String id) throws PersistenceException {
		return new JdbcTemplate< ContractRecord >()
				.forNamedSql( "TCONTRACTS_FINDBYID" )
				.withBinder(pst -> pst.setString(1, id))
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getOptionalResult();
	}

	 
	public List<ContractRecord> findAll() throws PersistenceException {
		return new JdbcTemplate<ContractRecord>()
				.forNamedSql( "TCONTRACTS_FINDALL" )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();

	}

	 
	public List<ContractRecord> findAllInForce() {
		return new JdbcTemplate<ContractRecord>()
				.forNamedSql( "TCONTRACTS_FINDALLINFORCE" )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

	 
	public List<ContractRecord> findByMechanicId(String id) {
		return new JdbcTemplate<ContractRecord>()
				.forNamedSql( "TCONTRACTS_FINDBYMECHANIC" )
				.withBinder( pst -> pst.setString(1, id) )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	
	}

	 
	public List<ContractRecord> findByProfessionalGroupId(String id) {
		return new JdbcTemplate<ContractRecord>()
				.forNamedSql( "TCONTRACTS_FINDBYPROFESSIONALGROUP" )
				.withBinder( pst -> pst.setString(1, id) )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

	 
	public List<ContractRecord> findByContractTypeId(String id2Del) {
		return new JdbcTemplate< ContractRecord >()
				.forNamedSql( "TCONTRACTS_FINDBYCONTRACTYYPEID" )
				.withBinder(pst -> pst.setString(1, id2Del))
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();

	}

	 
	public List<ContractRecord> findAllInForceThisMonth(LocalDate present) {
		return new JdbcTemplate< ContractRecord >()
				.forNamedSql( "TCONTRACTS_FINDALLINFORCETHISMONTH" )
				.withBinder(pst -> pst.setDate(1, Date.valueOf(present)))
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

	 
	public Optional<ContractRecord> findInForceForMechanic(String mechanicId) {
		return new JdbcTemplate< ContractRecord >()
				.forNamedSql( "TCONTRACTS_FINDINFORCEBYMECHANICID" )
				.withBinder(pst -> pst.setString(1, mechanicId))
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getOptionalResult();
	}

}
