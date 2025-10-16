package uo.ri.cws.application.persistence.mechanic;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord> {

	/**
	 * @param nif
	 * @return the mechanic dto identified by the nif or null if none
	 */
	Optional<MechanicRecord> findByNif(String nif) throws PersistenceException;

	public static class MechanicRecord extends BaseRecord {

		public String nif;
		public String name;
		public String surname;

	}
}
