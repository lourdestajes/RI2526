package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class ContractTypeGatewayImpl implements ContractTypeGateway {

	private RecordAssembler assembler = new RecordAssembler();

	 
	public void add(ContractTypeRecord t) throws PersistenceException {
		new JdbcTemplate<ContractTypeRecord>()
		.forNamedSql( "TCONTRACTTYPES_ADD" )
		.withBinder( pst -> {
            pst.setString(1, t.id);
            pst.setString(2, t.name);
            pst.setDouble(3, t.compensationDaysPerYear);
            pst.setTimestamp(4, Timestamp.valueOf(t.createdAt));
            pst.setTimestamp(5, Timestamp.valueOf(t.updatedAt));    

        })
		.execute();		
	}

	 
	public void remove(String id) throws PersistenceException {
		new JdbcTemplate<MechanicRecord>()
		.forNamedSql( "TCONTRACTTYPES_REMOVE" )
		.withBinder(pst -> pst.setString(1, id))
		.execute();		
	}

	 
	public void update(ContractTypeRecord t) throws PersistenceException {
		new JdbcTemplate<ContractTypeRecord>()
		.forNamedSql( "TCONTRACTTYPES_UPDATE" )
		.withBinder(pst -> {
			pst.setDouble(1, t.compensationDaysPerYear);
			pst.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			pst.setString(3, t.id);
		})
		.execute();		
	}

	 
	public Optional<ContractTypeRecord> findById(String id) throws PersistenceException {
		return new JdbcTemplate< ContractTypeRecord >()
				.forNamedSql("TCONTRACTTYPES_FINDBYID" )
				.withBinder( pst -> pst.setString(1, id) )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getOptionalResult();
	}

	 
	public Optional<ContractTypeRecord> findByName(String name) {
		return new JdbcTemplate< ContractTypeRecord >()
		.forNamedSql("TCONTRACTTYPES_FINDBYNAME" )
		.withBinder( pst -> pst.setString(1, name) )
		.withRowMapper( rs -> assembler.toRecord(rs) )
		.getOptionalResult();

	}

	 
	public List<ContractTypeRecord> findAll() {
		return new JdbcTemplate<ContractTypeRecord>()
				.forNamedSql( "TCONTRACTTYPES_FINDALL" )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

}
