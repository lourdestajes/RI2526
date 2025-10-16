package uo.ri.cws.application.persistence.provider.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class ProviderGatewayImpl implements ProviderGateway {

     
    public void add(ProviderRecord provider) throws PersistenceException {
    	new JdbcTemplate<ProviderRecord>()
    			.forNamedSql( "TPROVIDERS_ADD" )
				.withBinder(pst -> {
					pst.setString(1, provider.id);
					pst.setString(2, provider.name);
					pst.setString(3, provider.nif);
					pst.setString(4, provider.phone);
					pst.setString(5, provider.email);
	                pst.setTimestamp(6, Timestamp.valueOf(provider.createdAt));
	                pst.setTimestamp(7, Timestamp.valueOf(provider.updatedAt));    

				})
    			.execute();
    }

     
    public void remove(String id) throws PersistenceException {
    	new JdbcTemplate<ProviderRecord>()
    			.forNamedSql( "TPROVIDERS_REMOVE" )
                .withBinder(pst -> pst.setString(1, id))
                .execute();
    }

     
    public void update(ProviderRecord provider) throws PersistenceException {
    	new JdbcTemplate<ProviderRecord>()
    			.forNamedSql( "TPROVIDERS_UPDATE" )
    			.withBinder(pst -> {
    				pst.setString(1, provider.name);
    				pst.setString(2, provider.nif);
    				pst.setString(3, provider.phone);
    				pst.setString(4, provider.email);
    				pst.setLong(5, provider.version);
    				pst.setString(6, provider.id);
    			})
    			.execute();
    }

     
    public Optional<ProviderRecord> findById(String id) throws PersistenceException {
		return new JdbcTemplate<ProviderRecord>()
				.forNamedSql( "TPROVIDERS_FINDBYID" )
				.withBinder(pst -> pst.setString(1, id))
				.withRowMapper(Assembler::toProviderRecord)
				.getOptionalResult();
    }

     
    public List<ProviderRecord> findAll() throws PersistenceException {
		return new JdbcTemplate<ProviderRecord>()
				.forNamedSql( "TPROVIDERS_FINDALL" )
				.withRowMapper(Assembler::toProviderRecord)
				.getResultList();
    }

     
    public Optional<ProviderRecord> findByNif(String nif) throws PersistenceException {
		return new JdbcTemplate<ProviderRecord>()
				.forNamedSql( "TPROVIDERS_FINDBYNIF" )
				.withBinder(pst -> pst.setString(1, nif))
				.withRowMapper(Assembler::toProviderRecord)
				.getOptionalResult();
    }

	 
	public Optional<ProviderRecord> findByNameEmailPhone(String name, String email,
			String phone) throws PersistenceException {
		return new JdbcTemplate<ProviderRecord>()
				.forNamedSql( "TPROVIDERS_FINDBYDESCRIPTION" )
				.withBinder(pst -> {
					pst.setString(1, name);
					pst.setString(2, phone);
					pst.setString(3, email);
				})
				.withRowMapper(Assembler::toProviderRecord)
				.getOptionalResult();
    }

     
    public List<ProviderRecord> findByName(String name) throws PersistenceException {
    	return new JdbcTemplate<ProviderRecord>()
				.forNamedSql( "TPROVIDERS_FINDBYNAME" )
				.withBinder(pst -> 
					pst.setString(1, "%" + name.toLowerCase() + "%")
				)
				.withRowMapper(Assembler::toProviderRecord)
				.getResultList();
    }

     
    public List<ProviderRecord> findBySparePartCode(String code) throws PersistenceException {
    	return new JdbcTemplate<ProviderRecord>()
                .forNamedSql( "TPROVIDERS_FINDBYSPAREPARTCODE" )
                .withBinder(pst -> pst.setString(1, code))
                .withRowMapper(Assembler::toProviderRecord)
                .getResultList();
    }

}
