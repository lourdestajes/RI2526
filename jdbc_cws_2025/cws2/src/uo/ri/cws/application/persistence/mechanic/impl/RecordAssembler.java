package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class RecordAssembler extends BaseRecordAssembler<MechanicRecord> {

    public RecordAssembler() {
        super(MechanicRecord::new); // method reference to constructor
    }
    
     
    public MechanicRecord toRecord(ResultSet m) throws SQLException {
        MechanicRecord result = super.toRecord(m);
		result.nif = m.getString("nif");
		result.name = m.getString("name");
		result.surname = m.getString("surname");
        return result;
    }

}
