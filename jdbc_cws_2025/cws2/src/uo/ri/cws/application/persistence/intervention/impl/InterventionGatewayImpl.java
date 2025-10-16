package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class InterventionGatewayImpl implements InterventionGateway {

	private RecordAssembler assembler = new RecordAssembler();
	
	 
	public void add(InterventionRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	 
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	 
	public void update(InterventionRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	 
	public Optional<InterventionRecord> findById(String id)
			throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public List<InterventionRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public List<InterventionRecord> findByMechanicId(String id)
			throws PersistenceException {
		
		return new JdbcTemplate<InterventionRecord>()
			.forNamedSql( "TINTERVENTIONS_FINDBYMECHANICID" )
			.withBinder( pst -> pst.setString(1, id) )
			.withRowMapper( assembler::toInterventionRecord )
			.getResultList();
	}

	 
	public List<InterventionRecord> findByMechanicBetweenDates(String id, LocalDateTime start, LocalDateTime end) {
		return new JdbcTemplate<InterventionRecord>()
				.forNamedSql( "TINTERVENTIONS_FINDBYMECHANICIDBETWEENDATES" )
				.withBinder( pst -> { pst.setString(1, id);
							 		  pst.setTimestamp(2, Timestamp.valueOf(start));
							 		  if ( end != null ) {
							 			 pst.setTimestamp(3, Timestamp.valueOf(end));
							 		  }
							 		  else {
							 			  pst.setNull(3, java.sql.Types.TIMESTAMP);
							 		  }
				})
				.withRowMapper( assembler::toInterventionRecord )
				.getResultList();
	}

}
