package uo.ri.cws.application.persistence.professionalgroup;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public interface ProfessionalGroupGateway extends Gateway<ProfessionalGroupRecord>{

	public class ProfessionalGroupRecord extends BaseRecord {
		public String name;
		public double trienniumPayment;
		public double productivityRate; // The rate (decimal, e.g., 0.05 for 5%)
	}
	
	/**
	 * @param name
	 * @return the professional group
	 */
	Optional<ProfessionalGroupRecord> findByName(String name);
	
}
