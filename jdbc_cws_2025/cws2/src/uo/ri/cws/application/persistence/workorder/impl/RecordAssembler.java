package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class RecordAssembler extends BaseRecordAssembler<WorkOrderRecord>{

	public RecordAssembler() {
		super(WorkOrderRecord::new);
	}

	public WorkOrderRecord toRecord(ResultSet rs)
			throws SQLException {
		WorkOrderRecord result = super.toRecord(rs);
		result.vehicleId = rs.getString("vehicle_Id");
		result.description = rs.getString("description");
		result.date = rs.getTimestamp("date").toLocalDateTime();
		result.amount = rs.getDouble("amount");
		result.state = rs.getString("state");

		// might be null
		result.mechanicId = rs.getString("mechanic_Id");
		result.invoiceId = rs.getString("invoice_Id");

		return result;

	}


}
