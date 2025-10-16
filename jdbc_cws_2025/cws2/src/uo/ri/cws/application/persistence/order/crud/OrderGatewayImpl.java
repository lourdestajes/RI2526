package uo.ri.cws.application.persistence.order.crud;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class OrderGatewayImpl implements OrderGateway {

     
    public void add(OrderRecord order) throws PersistenceException {
    	new JdbcTemplate<OrderRecord>()
    			.forNamedSql( "TORDERS_ADD" )
				.withBinder(pst -> {
					pst.setString(1, order.id);
					pst.setDouble(2, order.amount);
					pst.setString(3, order.code);
					pst.setDate(4, Date.valueOf(order.orderedDate));
					pst.setString(5, order.state);
					pst.setString(6, order.providerId);
				})
				.execute();
    }

     
    public void remove(String id) throws PersistenceException {
    	new JdbcTemplate<OrderRecord>()
    			.forNamedSql( "TORDERS_REMOVE" )
    			.withBinder(pst -> pst.setString(1, id))
    			.execute();
    }

     
    public void update(OrderRecord order) throws PersistenceException {
		new JdbcTemplate<OrderRecord>()
				.forNamedSql( "TORDERS_UPDATE" )
				.withBinder(pst -> {
					pst.setDouble(1, order.amount);
					pst.setString(2, order.code);
					pst.setDate(3, Date.valueOf(order.orderedDate));
					pst.setDate(4, Date.valueOf(order.receptionDate));
					pst.setString(5, order.state);
					pst.setString(6, order.providerId);
					pst.setString(7, order.id);
				})
				.execute();
    }

     
    public Optional<OrderRecord> findById(String id) throws PersistenceException {
    	return new JdbcTemplate<OrderRecord>()
	    		.forNamedSql( "TORDERS_FINDBYID" )
	    		.withBinder(pst -> pst.setString(1, id) )
				.withRowMapper( Assembler::toRecord )
	    		.getOptionalResult();
    }

     
    public List<OrderRecord> findAll() throws PersistenceException {
		return new JdbcTemplate<OrderRecord>()
				.forNamedSql( "TORDERS_FINDALL" )
				.withRowMapper( Assembler::toRecord )
				.getResultList();
    }

	 
	public Optional<OrderRecord> findByCode(String code) {
		return new JdbcTemplate<OrderRecord>()
				.forNamedSql( "TORDERS_FINDBYCODE" )
				.withBinder(pst -> pst.setString(1, code))
				.withRowMapper( Assembler::toRecord )
				.getOptionalResult();
	}

	 
	public List<OrderRecord> findByProviderNif(String providerId) {
		return new JdbcTemplate<OrderRecord>()
				.forNamedSql( "TORDERS_FINDBYPROVIDERNIF" )
				.withBinder(pst -> pst.setString(1, providerId))
				.withRowMapper( Assembler::toRecord )
				.getResultList();
	}

}
