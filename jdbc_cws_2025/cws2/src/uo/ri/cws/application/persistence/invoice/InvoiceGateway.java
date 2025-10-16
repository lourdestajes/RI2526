package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

	/**
	 * @param invoice's number 
	 * @return invoice dto or null if it does not exist
	 */
	Optional<InvoiceRecord> findByNumber(Long number);
	
	/**
	 * @return next invoice number to assign; that is, one greater than the 
	 * 			greatest number already assigned to an invoice + 1 
	 * 
	 * Notice that, in a real deployment, this way to get a new invoice number 
	 * may produce incorrect values in a concurrent environment because two
	 * concurrent threads could get the same number.
	 * @throws PersistenceException 
	 *  
	 */
	Long getNextInvoiceNumber() throws PersistenceException;
	
	public static class InvoiceRecord extends BaseRecord {
		
		public double amount;	// total amount (money) vat included
		public double vat;		// amount of vat (money)
		public long number;		// the invoice identity, a sequential number
		public LocalDate date;	// of the invoice
		public String state;	// the state as in FacturaState
	}
}
