package uo.ri.cws.application.persistence.vehicle.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;

public class VehicleAssembler extends BaseRecordAssembler<VehicleRecord> {
    
	public VehicleAssembler() {
        super(VehicleRecord::new);
    }

     
    public VehicleRecord toRecord(ResultSet m) throws SQLException {
        VehicleRecord result = super.toRecord(m);
		result.plate = m.getString("plate");
        return result;
    }
}
