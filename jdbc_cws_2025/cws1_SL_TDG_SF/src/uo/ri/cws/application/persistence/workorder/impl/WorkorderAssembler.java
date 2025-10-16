package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;

public class WorkorderAssembler {

	public static Optional<WorkorderRecord> toWorkorderRecord(ResultSet rs) throws SQLException {
		Optional<WorkorderRecord> res = Optional.empty();
	    if (rs.next()) {
	    	WorkorderRecord wr = mapRowToWorkorderRecord(rs);
	        res = Optional.of(wr);
	    }
	    return res;
	}

	public static List<WorkorderRecord> toWorkorderList(ResultSet rs) throws SQLException {
	    List<WorkorderRecord> res = new ArrayList<>();
	    while (rs.next()) {
	        res.add(mapRowToWorkorderRecord(rs));
	    }
	    return res;
	}

	private static WorkorderRecord mapRowToWorkorderRecord(ResultSet rs) throws SQLException {
	    WorkorderRecord wr = new WorkorderRecord();
	    wr.id = rs.getString("id");
	    wr.version = rs.getLong("version");
	    wr.createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
	    wr.updatedAt = rs.getTimestamp("updatedAt").toLocalDateTime();
	    wr.entityState = rs.getString("entityState");
	    wr.description = rs.getString("description");
	    wr.date = rs.getTimestamp("date").toLocalDateTime();
	    wr.amount = rs.getDouble("amount");
	    wr.state = rs.getString("state");
	    wr.invoiceId = rs.getString("invoice_Id");
	    wr.mechanicId = rs.getString("mechanic_Id");
	    wr.vehicleId = rs.getString("vehicle_Id");
	    return wr;
	}


}
