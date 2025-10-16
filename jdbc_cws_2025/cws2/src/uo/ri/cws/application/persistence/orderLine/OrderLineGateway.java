package uo.ri.cws.application.persistence.orderLine;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway.OrderLineRecord;

public interface OrderLineGateway extends Gateway<OrderLineRecord> {
	
	public List<OrderLineRecord> findBySparePartId (String sparePartId) throws PersistenceException;
	
	public List<OrderLineRecord> findByOrderId (String orderId) throws PersistenceException;

	public static class OrderLineRecord {
		
		public double price;
		public int quantity;
		public String sparePartId;
		public String orderId;
		
	}

}
