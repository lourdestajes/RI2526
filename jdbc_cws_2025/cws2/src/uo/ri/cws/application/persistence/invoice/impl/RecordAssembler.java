package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public class RecordAssembler extends BaseRecordAssembler<InvoiceRecord> {
    
	public RecordAssembler() {
        super(InvoiceRecord::new);
    }

	public InvoiceRecord toRecord(ResultSet rs)
			throws SQLException {
		
		InvoiceRecord result = super.toRecord(rs);

		result.date = rs.getDate("date").toLocalDate();
		result.number = rs.getLong("number");
		result.state = rs.getString("state");
		result.vat = rs.getDouble("vat");

		return result;
	}

}
