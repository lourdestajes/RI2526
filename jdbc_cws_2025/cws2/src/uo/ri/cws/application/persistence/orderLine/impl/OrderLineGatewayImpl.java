package uo.ri.cws.application.persistence.orderLine.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class OrderLineGatewayImpl implements OrderLineGateway {

     
    public void add(OrderLineRecord orderLine) throws PersistenceException {
    	new JdbcTemplate<OrderLineRecord>()
	    	 .forNamedSql( "TORDERLINES_ADD" )
	    	 .withBinder(pst -> {
	    		 pst.setDouble(1, orderLine.price);
	    		 pst.setInt(2, orderLine.quantity);
	    		 pst.setString(3, orderLine.sparePartId);
	    		 pst.setString(4, orderLine.orderId);
	    	 })
	    	 .execute();
    }

     
    public void remove(String id) throws PersistenceException {
		new JdbcTemplate<OrderLineRecord>()
				.forNamedSql( "TORDERLINES_REMOVE" )
				.withBinder(pst -> pst.setString(1, id))
				.execute();
    }

     
    public void update(OrderLineRecord orderLine) throws PersistenceException {
		new JdbcTemplate<OrderLineRecord>()
				.forNamedSql( "TORDERLINES_UPDATE" )
				.withBinder(pst -> {
					pst.setDouble(1, orderLine.price);
					pst.setInt(2, orderLine.quantity);
					pst.setString(3, orderLine.sparePartId);
					pst.setString(4, orderLine.orderId);
				})
				.execute();
    }

     
    public Optional<OrderLineRecord> findById(String id) throws PersistenceException {
    	return null;
    }

     
    public List<OrderLineRecord> findAll() throws PersistenceException {
    	return new JdbcTemplate<OrderLineRecord>()
				.forNamedSql( "TORDERLINES_FINDALL" )
				.withRowMapper( Assembler::toOrderLineRecord )
				.getResultList();
    }

     
    public List<OrderLineRecord> findBySparePartId(String sparePartId) throws PersistenceException {
    	return new JdbcTemplate<OrderLineRecord>()
                .forNamedSql( "TORDERLINES_FINDBYSPAREPART" )
                .withBinder( pst -> pst.setString(1, sparePartId) )
                .withRowMapper( Assembler::toOrderLineRecord )
                .getResultList();
    }

     
    public List<OrderLineRecord> findByOrderId(String orderId) throws PersistenceException {
		return new JdbcTemplate<OrderLineRecord>()
				.forNamedSql( "TORDERLINES_FINDBYORDERID" )
				.withBinder(pst -> pst.setString(1, orderId))
				.withRowMapper(Assembler::toOrderLineRecord)
				.getResultList();
    }

}
