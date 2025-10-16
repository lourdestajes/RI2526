package uo.ri.cws.application.persistence.substitution.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway.SubstitutionRecord;

public class RecordAssembler extends BaseRecordAssembler<SubstitutionRecord>{

	public RecordAssembler() {
		super(SubstitutionRecord::new);
	}

	public static List<SubstitutionRecord> toSubstitutionList(ResultSet rs)
			throws SQLException {
		List<SubstitutionRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toSubstitutionRecord(rs));
		}

		return res;
	}

	public static SubstitutionRecord toSubstitutionRecord(ResultSet rs)
			throws SQLException {
		SubstitutionRecord result = new SubstitutionRecord();
		result.id = rs.getString("id");
		result.quantity = rs.getLong("quantity");
		result.intervention_id = rs.getString("intervention_id");
		result.sparePart_id = rs.getString("sparePart_id");
		return result;
	}

}
