package uo.ri.cws.application.persistence.sparePart;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway.SparePartRecord;

public interface SparePartGateway extends Gateway<SparePartRecord> {
	
	public Optional<SparePartRecord> findByCode(String code) throws PersistenceException; 
	
	public class SparePartRecord extends BaseRecord {
		public String code;
		public String description;
		public int stock;
		public int minStock;
		public int maxStock;
		public double price;
	}

	
}
