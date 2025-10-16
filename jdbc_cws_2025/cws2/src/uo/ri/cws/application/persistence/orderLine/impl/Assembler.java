package uo.ri.cws.application.persistence.orderLine.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.orderLine.OrderLineGateway.OrderLineRecord;

public class Assembler {

	public static List<OrderLineRecord> toOrderLineRecordList(ResultSet rs)
			throws SQLException {
		List<OrderLineRecord> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toOrderLineRecord(rs));
		}
		return res;
	}

	public static OrderLineRecord toOrderLineRecord(ResultSet rs)
			throws SQLException {
		OrderLineRecord result = new OrderLineRecord();
		result.sparePartId = rs.getString("sparepart_id");
		result.orderId = rs.getString("order_id");
		result.price = rs.getDouble("price");
		result.quantity = rs.getInt("quantity");
		return result;
	}

}
