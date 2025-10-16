package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public class RecordAssembler extends BaseRecordAssembler<InterventionRecord>{

	public RecordAssembler() {
		super(InterventionRecord::new);
	}

	public InterventionRecord toInterventionRecord(ResultSet rs)
			throws SQLException {
		
		InterventionRecord result = super.toRecord(rs);
		result.mechanicId = rs.getString("mechanic_id");
		result.workorderId = rs.getString("workorder_id");
		result.date = rs.getDate("date");
		result.minutes = rs.getInt("minutes");
		
		return result;
	}

}
