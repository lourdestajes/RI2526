package uo.ri.cws.application.persistence.vehicle.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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


    public Optional<VehicleRecord> toOptionalRecord(ResultSet rs) throws SQLException {
        Optional<VehicleRecord> result = Optional.empty();
        if (rs.next()) {
            result = Optional.of(toValue(rs));
        }
        return result;
    }


    private VehicleRecord toValue(ResultSet rs) throws SQLException {
        VehicleRecord result = super.toRecord(rs);
        result.plate = rs.getString("plate");
        return result;    
    }
}
