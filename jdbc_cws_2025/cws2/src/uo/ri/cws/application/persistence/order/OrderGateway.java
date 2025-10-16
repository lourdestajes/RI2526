package uo.ri.cws.application.persistence.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;

public interface OrderGateway extends Gateway<OrderRecord> {

	public Optional<OrderRecord> findByCode(String code);

	public List<OrderRecord> findByProviderNif(String providerId);

	public static class OrderRecord {
		public String id;
		public long version;

		public String code;
		public LocalDate orderedDate;
		public LocalDate receptionDate;
		public double amount;
		public String state;

		public String providerId;
	}
	
}
