package uo.ri.cws.application.persistence.substitution;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway.SubstitutionRecord;

public interface SubstitutionGateway extends Gateway<SubstitutionRecord> {
	
	public List<SubstitutionRecord> findBySparePartId(String sparePartId)throws PersistenceException;
	
	public class SubstitutionRecord extends BaseRecord {

		public String id;
		public Long quantity;
		public String intervention_id;
		public String sparePart_id;
	}


}
