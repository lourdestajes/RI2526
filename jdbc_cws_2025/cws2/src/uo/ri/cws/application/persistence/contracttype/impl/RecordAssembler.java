package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

public class RecordAssembler extends BaseRecordAssembler<ContractTypeRecord> {
    public RecordAssembler() {
        super(ContractTypeRecord::new);
    }

     
    public ContractTypeRecord toRecord(ResultSet m) throws SQLException {
    	ContractTypeRecord result = super.toRecord(m);

		result.name = m.getString("name");
		result.compensationDaysPerYear = m.getDouble("compensationDaysperyear");
        return result;
    }
}