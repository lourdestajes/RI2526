package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

	private RecordAssembler assembler = new RecordAssembler();
	
	 
	public void add(MechanicRecord mechanic) throws PersistenceException {
		new JdbcTemplate<MechanicRecord>()
			.forNamedSql( "TMECHANICS_ADD" )
			.withBinder( pst -> {
                pst.setString(1, mechanic.id);
                pst.setString(2, mechanic.nif);
                pst.setString(3, mechanic.name);
                pst.setString(4, mechanic.surname);
                pst.setTimestamp(5, Timestamp.valueOf(mechanic.createdAt));
                pst.setTimestamp(6, Timestamp.valueOf(mechanic.updatedAt));    
			})
			.execute();
	}

	 
	public void remove(String id) throws PersistenceException {
		new JdbcTemplate<MechanicRecord>()
				.forNamedSql( "TMECHANICS_REMOVE" )
				.withBinder(pst -> pst.setString(1, id))
				.execute();
	}

	 
	public void update(MechanicRecord t) throws PersistenceException {
		new JdbcTemplate<MechanicRecord>()
				.forNamedSql( "TMECHANICS_UPDATE" )
				.withBinder(pst -> {
					pst.setString(1, t.name);
					pst.setString(2, t.surname);
					pst.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
					pst.setString(4, t.id);
				})
				.execute();
	}

	 
	public Optional<MechanicRecord> findById(String id) throws PersistenceException {
		Optional<MechanicRecord> optional = Optional.empty();
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYID"))) {
			pst.setString(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				optional = assembler.toOptionalRecord(rs);
			}
		} catch(SQLException e) {
			throw new PersistenceException(e);
		}
		return optional;
//		return new JdbcTemplate< MechanicRecord >()
//				.forNamedSql( "TMECHANICS_FINDBYID" )
//				.withBinder(pst -> pst.setString(1, id))
//				.withRowMapper( rs -> assembler.toRecord(rs) )
//				.getOptionalResult();
	}

	 
	public List<MechanicRecord> findAll() throws PersistenceException {
		return new JdbcTemplate<MechanicRecord>()
				.forNamedSql( "TMECHANICS_FINDALL" )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

	 
	public Optional<MechanicRecord> findByNif(String nif) throws PersistenceException {
		return new JdbcTemplate< MechanicRecord >()
				.forNamedSql("TMECHANICS_FINDBYNIF" )
				.withBinder( pst -> pst.setString(1, nif) )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getOptionalResult();
	}


	@Override
	public List<MechanicRecord> findBySalaryGreater(double wage) {
		
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYWAGE"))) {
			pst.setDouble(1, wage);
			try (ResultSet rs = pst.executeQuery()) {
				return assembler.toRecordList(rs);
			}
		} catch(SQLException e) {
			throw new PersistenceException(e);
		}
		
	}

}
