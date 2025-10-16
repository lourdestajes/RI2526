package uo.ri.cws.application.persistence.supply.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;

public class Assembler {

	

	

// Extension

	public static SupplyRecord toSupplyRecord(ResultSet rs)
			throws SQLException {

		SupplyRecord record = new SupplyRecord();
		record.id = rs.getString("id");
		record.deliveryTerm = rs.getInt("deliveryTerm");
		record.price = rs.getDouble("price");
		record.providerId = rs.getString("provider_id");
		record.sparePartId = rs.getString("sparePart_id");

		return record;
	}

	public static List<SupplyRecord> toSupplyRecordList(ResultSet rs)
			throws SQLException {
		List<SupplyRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toSupplyRecord(rs));
		}

		return res;
	}

}
