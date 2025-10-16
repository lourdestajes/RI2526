package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.base.BaseRecordAssembler;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public class ProfessionalGroupAssembler extends BaseRecordAssembler<ProfessionalGroupRecord> {
    public ProfessionalGroupAssembler() {
        super(ProfessionalGroupRecord::new);
    }

     
    public ProfessionalGroupRecord toRecord(ResultSet m) throws SQLException {
    	ProfessionalGroupRecord result = super.toRecord(m);

		result.name = m.getString("name");
		result.trienniumPayment = m.getDouble("trienniumPayment");
		result.productivityRate = m.getDouble("productivityRate");
        return result;
    }
}