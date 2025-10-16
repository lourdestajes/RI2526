package uo.ri.cws.application.persistence.provider.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

public class Assembler {

	public static ProviderRecord toProviderRecord(ResultSet rs)
			throws SQLException {
		ProviderRecord res = new ProviderRecord();
		res.id = rs.getString("id");
		res.name = rs.getString("name");
		res.nif = rs.getString("nif");
		res.email = rs.getString("email");
		res.phone = rs.getString("phone");
		return res;
	}

	public static List<ProviderRecord> toProviderRecordList(ResultSet rs)
			throws SQLException {
		List<ProviderRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toProviderRecord(rs));
		}

		return res;
	}

}
