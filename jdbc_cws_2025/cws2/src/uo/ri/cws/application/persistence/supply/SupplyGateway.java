package uo.ri.cws.application.persistence.supply;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;

public interface SupplyGateway extends Gateway<SupplyRecord> {
	
	public List<SupplyRecord> findBySparePartId(String sparePartId) throws PersistenceException;
	
	public List<SupplyRecord> findByProviderId(String providerId) throws PersistenceException;
	
	public Optional<SupplyRecord> findByProviderAndSparePartIds(
			String providerId, 
			String sparePartId) throws PersistenceException;
	
	Optional<SupplyRecord> findByProviderNifAndSparePartCode(
			String providerNif,
			String sparePartCode) throws PersistenceException;

	public static class SupplyRecord {
		public String id;
		public long version;

		public String providerId;
		public String sparePartId;

		public double price;
		public int deliveryTerm;
	}

}
