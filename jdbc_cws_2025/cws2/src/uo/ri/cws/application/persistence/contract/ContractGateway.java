package uo.ri.cws.application.persistence.contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public interface ContractGateway extends Gateway<ContractRecord> {

	public class ContractRecord extends BaseRecord {
		public String mechanicId;
		public String contractTypeId;
		public String professionalGroupId;
	    
	    public double annualBaseSalary;
	    public LocalDate startDate;
	    public LocalDate endDate;
	    public double settlement; 	// liquidación
	    public double taxRate;  	// not in %	but as a decimal
	    public String state;
	}
    
    /**
     * @return a list with all contracts in force (might be empty) 
     */
    List<ContractRecord> findAllInForce();

	List<ContractRecord> findByMechanicId(String id);

	List<ContractRecord> findByProfessionalGroupId(String id);
	
	List<ContractRecord> findByContractTypeId(String id2Del);

	List<ContractRecord> findAllInForceThisMonth(LocalDate present);

	Optional<ContractRecord> findInForceForMechanic(String mechanicId);

}
