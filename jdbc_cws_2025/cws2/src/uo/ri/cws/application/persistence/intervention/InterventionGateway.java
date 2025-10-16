package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

	/**
	 * @param id, refers to the mechanic id
	 * @return a list with all interventions done by the mechanic
	 * or an empty list if there are none
	 * @throws PersistenceException 
	 */
	List<InterventionRecord> findByMechanicId(String id) throws PersistenceException;
	
	List<InterventionRecord> findByMechanicBetweenDates(String m, LocalDateTime start, LocalDateTime end);

	public class InterventionRecord extends BaseRecord {
		public String workorderId;
		public String mechanicId;
		public Date date;
		public int minutes;
	}


}
