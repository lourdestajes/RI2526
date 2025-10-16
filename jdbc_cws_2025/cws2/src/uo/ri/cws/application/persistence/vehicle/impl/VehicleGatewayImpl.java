package uo.ri.cws.application.persistence.vehicle.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;

public class VehicleGatewayImpl implements VehicleGateway {

	private VehicleAssembler assembler = new VehicleAssembler();

	 
	public void add(VehicleRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	 
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	 
	public void update(VehicleRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	 
	public Optional<VehicleRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	 
	public List<VehicleRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public Optional<VehicleRecord> findByPlate(String plate) throws PersistenceException {
		return new JdbcTemplate< VehicleRecord >()
		.forNamedSql("TVEHICLES_FINDBYPLATE" )
		.withBinder( pst -> pst.setString(1, plate) )
		.withRowMapper( rs -> assembler.toRecord(rs) )
		.getOptionalResult();
	}
	
//	 
//	public void add(MechanicRecord mechanic) throws PersistenceException {
//		new JdbcTemplate<MechanicRecord>()
//			.forNamedSql( "TMECHANICS_ADD" )
//			.withBinder( pst -> {
//                pst.setString(1, mechanic.id);
//                pst.setString(2, mechanic.nif);
//                pst.setString(3, mechanic.name);
//                pst.setString(4, mechanic.surname);
//            })
//			.execute();
//	}
//
//	 
//	public void remove(String id) throws PersistenceException {
//		new JdbcTemplate<MechanicRecord>()
//				.forNamedSql( "TMECHANICS_REMOVE" )
//				.withBinder(pst -> pst.setString(1, id))
//				.execute();
//	}
//
//	 
//	public void update(MechanicRecord t) throws PersistenceException {
//		new JdbcTemplate<MechanicRecord>()
//				.forNamedSql( "TMECHANICS_UPDATE" )
//				.withBinder(pst -> {
//					pst.setString(1, t.name);
//					pst.setString(2, t.surname);
//					pst.setString(3, t.id);
//				})
//				.execute();
//	}
//
//	 
//	public Optional<MechanicRecord> findById(String id) throws PersistenceException {
//		return new JdbcTemplate< MechanicRecord >()
//				.forNamedSql( "TMECHANICS_FINDBYID" )
//				.withBinder(pst -> pst.setString(1, id))
//				.withRowMapper( rs -> assembler.toRecord(rs) )
//				.getOptionalResult();
//	}
//
//	 
//	public List<MechanicRecord> findAll() throws PersistenceException {
//		return new JdbcTemplate<MechanicRecord>()
//				.forNamedSql( "TMECHANICS_FINDALL" )
//				.withRowMapper( rs -> assembler.toRecord(rs) )
//				.getResultList();
//	}
//
//	 
//	public Optional<MechanicRecord> findByNif(String nif) throws PersistenceException {
//		return new JdbcTemplate< MechanicRecord >()
//				.forNamedSql("TMECHANICS_FINDBYNIF" )
//				.withBinder( pst -> pst.setString(1, nif) )
//				.withRowMapper( rs -> assembler.toRecord(rs) )
//				.getOptionalResult();
//	}

}
