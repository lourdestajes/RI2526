package uo.ri.cws.application.repository;

import java.util.Optional;

import uo.ri.cws.domain.Invoice;

public interface InvoiceRepository extends Repository<Invoice> {

	/**
	 * @param invoice number
	 * @return an optional with the invoice with the given number
	 */
	Optional<Invoice> findByNumber(Long numero);
	
	/**
	 * @return the next invoice number
	 * Note: the number generation must be done in a transactionally safe way as
	 *    - it is not allowed to have two invoices with the same number and 
	 *    - the number must be strictly sequential. I.e., the next invoice number must be
	 * the highest existing number + 1. 
	 *    - There could not exist gaps in the sequence.
	 */
	Long getNextInvoiceNumber();
}
