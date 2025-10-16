package uo.ri.cws.application.persistence.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

	public long findNextInvoiceNumber();
	
	public class InvoiceRecord {
        public String id;
        public long version;
        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;
        
        public Long number;
        public Double amount;
        public Double vat;
        public LocalDate date;
        public String status;
    }

}
