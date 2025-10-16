package uo.ri.cws.application.persistence.sparePart.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway.SparePartRecord;

public class RecordAssembler extends BaseRecordAssembler<SparePartRecord>{

	public RecordAssembler() {
		super(SparePartRecord::new);
	}

	public SparePartRecord toSparePartRecord(ResultSet rs)
			throws SQLException {
		SparePartRecord record = super.toRecord(rs);
		record.code = rs.getString("code");
		record.description = rs.getString("description");
		record.maxStock = rs.getInt("maxstock");
		record.minStock = rs.getInt("minstock");
		record.price = rs.getDouble("price");
		record.stock = rs.getInt("stock");

		return record;
	}

}
