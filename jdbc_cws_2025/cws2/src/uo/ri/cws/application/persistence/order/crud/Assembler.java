package uo.ri.cws.application.persistence.order.crud;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.order.OrderGateway.OrderRecord;

public class Assembler {

	public static OrderRecord toRecord(ResultSet rs) throws SQLException {
		OrderRecord res = new OrderRecord();
		
		res.id = rs.getString("id");
		res.version = rs.getLong("version");
		
		res.amount = rs.getDouble("amount");
		res.code = rs.getString("code");
		res.orderedDate = rs.getDate("ordereddate").toLocalDate();

		Date receptionDate = rs.getDate("receptiondate");
		res.receptionDate = (receptionDate != null)
				? receptionDate.toLocalDate()
                : null;

		res.state = rs.getString("state");
		res.providerId = rs.getString("provider_id");
		
		return res;
	}

}
