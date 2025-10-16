package uo.ri.cws.application.persistence.supply.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class SupplyGatewayImpl implements SupplyGateway {

     
    public void add(SupplyRecord t) throws PersistenceException {
    	new JdbcTemplate<SupplyRecord>()
	    	.forNamedSql( "TSUPPLIES_ADD" )
	    	.withBinder( pst -> {
	    		pst.setString(1, t.id);
	            pst.setLong(2, t.deliveryTerm);
	            pst.setDouble(3, t.price);
	            pst.setString(4, t.providerId);
	            pst.setString(5, t.sparePartId);
	        })
	    	.execute();
    }

     
    public void remove(String id) throws PersistenceException {
    	new JdbcTemplate<SupplyRecord>()
            .forNamedSql( "TSUPPLIES_REMOVE" )
            .withBinder(pst -> pst.setString(1, id))
            .execute();
    }

     
    public void update(SupplyRecord t) throws PersistenceException {
		new JdbcTemplate<SupplyRecord>()
				.forNamedSql( "TSUPPLIES_UPDATE" )
				.withBinder(pst -> {
					pst.setLong(1, t.deliveryTerm);
					pst.setDouble(2, t.price);
					pst.setString(3, t.providerId);
					pst.setString(4, t.sparePartId);
					pst.setLong(5, t.version);
					pst.setString(6, t.id);
				})
				.execute();
    }

     
    public Optional<SupplyRecord> findById(String id) throws PersistenceException {
    	return new JdbcTemplate<SupplyRecord>()
                .forNamedSql( "TSUPPLIES_FINDBYID" )
                .withBinder(pst -> pst.setString(1, id))
                .withRowMapper(Assembler::toSupplyRecord)
                .getOptionalResult();
    }

     
    public List<SupplyRecord> findAll() throws PersistenceException {
		return new JdbcTemplate<SupplyRecord>()
				.forNamedSql( "TSUPPLIES_FINDALL" )
				.withRowMapper(Assembler::toSupplyRecord)
				.getResultList();
    }

     
    public List<SupplyRecord> findBySparePartId(String sparePartId) throws PersistenceException {
		return new JdbcTemplate<SupplyRecord>()
				.forNamedSql( "TSUPPLIES_FINDBYSPAREPARTID" )
				.withBinder(pst -> pst.setString(1, sparePartId))
				.withRowMapper(Assembler::toSupplyRecord)
				.getResultList();
    }

     
    public List<SupplyRecord> findByProviderId(String providerId) throws PersistenceException {
		return new JdbcTemplate<SupplyRecord>()
				.forNamedSql( "TSUPPLIES_FINDBYPROVIDERID" )
				.withBinder(pst -> pst.setString(1, providerId))
				.withRowMapper(Assembler::toSupplyRecord)
				.getResultList();
    }

     
	public Optional<SupplyRecord> findByProviderAndSparePartIds(String providerId,
			String sparePartId) throws PersistenceException {
		return new JdbcTemplate<SupplyRecord>()
				.forNamedSql( "TSUPPLIES_FINDBYPROVIDERANDSPAREPARTIDS" )
				.withBinder(pst -> {
					pst.setString(1, providerId);
					pst.setString(2, sparePartId);
				})
				.withRowMapper(Assembler::toSupplyRecord)
                .getOptionalResult();
    }

     
	public Optional<SupplyRecord> findByProviderNifAndSparePartCode(
			String providerNif,
			String sparePartCode) throws PersistenceException {
    	
		return new JdbcTemplate<SupplyRecord>()
				.forNamedSql( "TSUPPLIES_FINDBYPROVIDERNIFANDSPAREPARTCODE" )
				.withBinder(pst -> {
					pst.setString(1, providerNif);
					pst.setString(2, sparePartCode);
				})
				.withRowMapper(Assembler::toSupplyRecord)
                .getOptionalResult();
    }
}
