package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public class RecordAssembler extends BaseRecordAssembler<ContractRecord> {
    public RecordAssembler() {
        super(ContractRecord::new);
    }

     
    public ContractRecord toRecord(ResultSet m) throws SQLException {
    	ContractRecord result = super.toRecord(m);
    	result.mechanicId = m.getString("mechanic_id");
    	result.professionalGroupId = m.getString("professionalgroup_id");
    	result.contractTypeId = m.getString("contracttype_id");
    	result.annualBaseSalary = m.getDouble("annualBaseSalary");
		result.settlement = m.getDouble("settlement");
		result.startDate = m.getDate("startDate").toLocalDate();
		Date end = m.getDate("endDate");
		result.endDate = (end == null)? null:end.toLocalDate();
		result.state = m.getString("state");
		result.taxRate = m.getDouble("taxRate");
        return result;
    }
}

