package uo.ri.cws.application.persistence.contracttype;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

public interface ContractTypeGateway extends Gateway<ContractTypeRecord>{

	public class ContractTypeRecord extends BaseRecord {
	    public String name;
	    public double compensationDaysPerYear;	
	}
	
	/**
	 * @param name
	 * @return the contract type object 
	 */
	Optional<ContractTypeRecord> findByName(String name);

	/**
	 * @return the contract types 
	 */
	List<ContractTypeRecord> findAll();
}
