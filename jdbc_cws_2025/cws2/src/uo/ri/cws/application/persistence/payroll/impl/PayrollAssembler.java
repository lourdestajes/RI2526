package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public class PayrollAssembler extends BaseRecordAssembler<PayrollRecord> {
    public PayrollAssembler() {
        super(PayrollRecord::new);
    }

     
    public PayrollRecord toRecord(ResultSet m) throws SQLException {
    	PayrollRecord result = super.toRecord(m);
		result.id = m.getString("id");
		result.baseSalary = m.getDouble("baseSalary");
		result.contract_id = m.getString("contract_id");
		result.date = m.getDate("date").toLocalDate();
		result.extraSalary = m.getDouble("extraSalary");
		result.productivityEarning = m.getDouble("productivityEarning");
		result.trienniumEarning = m.getDouble("trienniumEarning");
		result.nicDeduction = m.getDouble("nicDeduction");
		result.taxDeduction = m.getDouble("taxDeduction");

        return result;
    }
}

