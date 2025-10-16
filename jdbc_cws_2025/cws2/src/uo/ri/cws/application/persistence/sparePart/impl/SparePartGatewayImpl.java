package uo.ri.cws.application.persistence.sparePart.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class SparePartGatewayImpl implements SparePartGateway {

	private RecordAssembler assembler = new RecordAssembler();
	
     
    public void add(SparePartRecord t) throws PersistenceException {
    	new JdbcTemplate<SparePartRecord>()
    			.forNamedSql( "TSPAREPARTS_ADD" )
    			.withBinder(pst -> {
    				pst.setString(1, t.id);
    				pst.setString(2, t.code);
    				pst.setString(3, t.description);
    				pst.setInt(4, t.maxStock);
    				pst.setInt(5, t.minStock);
    				pst.setDouble(6, t.price);
    				pst.setInt(7, t.stock);
    			})
    			.execute();
    }

     
    public void remove(String id) throws PersistenceException {
		new JdbcTemplate<SparePartRecord>()
				.forNamedSql( "TSPAREPARTS_REMOVE" )
				.withBinder(pst -> pst.setString(1, id))
				.execute();
    }

     
    public void update(SparePartRecord t) throws PersistenceException {
		new JdbcTemplate<SparePartRecord>()
				.forNamedSql( "TSPAREPARTS_UPDATE" )
				.withBinder(pst -> {
					pst.setString(1, t.code);
					pst.setString(2, t.description);
					pst.setInt(3, t.maxStock);
					pst.setInt(4, t.minStock);
					pst.setDouble(5, t.price);
					pst.setInt(6, t.stock);
					pst.setLong(7, t.version);
					pst.setString(8, t.id);
				})
				.execute();
    }

     
    public Optional<SparePartRecord> findById(String id) throws PersistenceException {
		return new JdbcTemplate<SparePartRecord>()
				.forNamedSql( "TSPAREPARTS_FINDBYID" )
				.withBinder(pst -> pst.setString(1, id))
				.withRowMapper(assembler::toSparePartRecord)
				.getOptionalResult();
    }

     
    public List<SparePartRecord> findAll() throws PersistenceException {
		return new JdbcTemplate<SparePartRecord>()
				.forNamedSql( "TSPAREPARTS_FINDALL" )
				.withRowMapper(assembler::toSparePartRecord)
				.getResultList();
    }

     
    public Optional<SparePartRecord> findByCode(String code) throws PersistenceException {
		return new JdbcTemplate<SparePartRecord>()
				.forNamedSql( "TSPAREPARTS_FINDBYCODE" )
				.withBinder(pst -> pst.setString(1, code))
				.withRowMapper(assembler::toSparePartRecord)
				.getOptionalResult();
    }

}
