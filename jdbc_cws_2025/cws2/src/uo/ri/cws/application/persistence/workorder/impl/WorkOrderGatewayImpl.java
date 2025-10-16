package uo.ri.cws.application.persistence.workorder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	private RecordAssembler assembler = new RecordAssembler();
	
	 
	public void add(WorkOrderRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
	}

	 
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
	}

	 
	public void update(WorkOrderRecord t) throws PersistenceException {
		new JdbcTemplate<WorkOrderRecord>()
				.forNamedSql( "TWORKORDERS_UPDATE" )
				.withBinder(pst -> {
					pst.setString(1, t.vehicleId);
					pst.setString(2, t.description);
					pst.setTimestamp(3, java.sql.Timestamp.valueOf( t.date ));
					pst.setDouble(4, t.amount);
					pst.setString(5, t.state);
					if (t.mechanicId == null) {
                        pst.setNull(6, java.sql.Types.VARCHAR);
                    } else {
                        pst.setString(6, t.mechanicId);
                    }
					if (t.invoiceId == null) {
                        pst.setNull(7, java.sql.Types.VARCHAR);
                    } else {
                        pst.setString(7, t.invoiceId);
                    }
					pst.setString(8, t.id);
				})
				.execute();
	}

	 
	public Optional<WorkOrderRecord> findById(String id) throws PersistenceException {
		return new JdbcTemplate< WorkOrderRecord >()
				.forNamedSql( "TWORKORDERS_FINDBYID" )
				.withBinder(pst -> pst.setString(1, id))
				.withRowMapper( assembler::toRecord )
				.getOptionalResult();
	}

	 
	public List<WorkOrderRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public List<WorkOrderRecord> findByIds(List<String> ids) throws PersistenceException {
		List<WorkOrderRecord> result = new ArrayList<>();
		JdbcTemplate<WorkOrderRecord> query = 
			new JdbcTemplate<WorkOrderRecord>()
				.forNamedSql( "TWORKORDERS_FINDBYIDS" )
				.withRowMapper(assembler::toRecord);

		for(String workOrderID : ids) {
			result.addAll(
				query
					.withBinder(pst -> pst.setString(1, workOrderID))
					.getResultList()
			);
		}
		return result;
	}

	 
	public List<WorkOrderRecord> findByVehicleId(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public List<WorkOrderRecord> findByMechanicId(String id) throws PersistenceException {
		return new JdbcTemplate<WorkOrderRecord>()
				.forNamedSql( "TWORKORDERS_FINDBYMECHANICID" )
				.withBinder(pst -> pst.setString(1, id))
				.withRowMapper(assembler::toRecord)
				.getResultList();
	}

	 
	public List<WorkOrderRecord> findByState(String state) {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public List<WorkOrderRecord> findNotInvoicedByClientNif(String nif) {
		return new JdbcTemplate<WorkOrderRecord>()
				.forNamedSql( "TWORKORDERS_FIND_NOT_INVOICED_BY_CLIENT_NIF" )
				.withBinder(pst -> pst.setString(1, nif))
				.withRowMapper(assembler::toRecord)
				.getResultList();
	}

}
