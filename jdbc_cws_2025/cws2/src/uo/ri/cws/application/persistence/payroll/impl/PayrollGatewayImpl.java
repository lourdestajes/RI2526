package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.util.jdbc.JdbcTemplate;

public class PayrollGatewayImpl implements PayrollGateway {

	private PayrollAssembler assembler = new PayrollAssembler();
	
	 
	public void add(PayrollRecord r) throws PersistenceException {
		new JdbcTemplate<PayrollRecord>()
		.forNamedSql( "TPAYROLLS_ADD" )
		.withBinder( pst -> {
            pst.setString(1, r.id);
            pst.setString(2, r.entityState);
            pst.setString(3, r.contract_id);
            pst.setDate(4, Date.valueOf(r.date) );
            pst.setDouble(5, r.baseSalary);
            pst.setDouble(6, r.extraSalary);
            pst.setDouble(7, r.productivityEarning);
            pst.setDouble(8, r.trienniumEarning);
            pst.setDouble(9, r.taxDeduction);
            pst.setDouble(10, r.nicDeduction);
            pst.setTimestamp(11, Timestamp.valueOf(r.createdAt));
            pst.setTimestamp(12, Timestamp.valueOf(r.updatedAt));
        })
		.execute();

	}

	 
	public void remove(String id) throws PersistenceException {
		new JdbcTemplate<PayrollRecord>()
		.forNamedSql( "TPAYROLLS_REMOVE" )
		.withBinder(pst -> pst.setString(1, id))
		.execute();
	}

	 
	public void update(PayrollRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	 
	public Optional<PayrollRecord> findById(String id) throws PersistenceException {
		return new JdbcTemplate< PayrollRecord >()
				.forNamedSql( "TPAYROLLS_FINDBYID" )
				.withBinder(pst -> pst.setString(1, id))
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getOptionalResult();
	}

	 
	public List<PayrollRecord> findByContractId(String contractId) {
		return new JdbcTemplate<PayrollRecord>()
				.forNamedSql( "TPAYROLLS_FINDBYCONTRACT" )
				.withBinder( pst -> pst.setString(1, contractId) )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

	 
	public List<PayrollRecord> findLastMonthPayrolls() {
		return new JdbcTemplate<PayrollRecord>()
				.forNamedSql( "TPAYROLLS_FINDLASTMONTHPAYROLLS" )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

	 
	public Optional<PayrollRecord> findLastMonthPayrollForContract(String contractId) {
		return new JdbcTemplate< PayrollRecord >()
				.forNamedSql( "TPAYROLLS_FINDLASTFORCONTRACT" )
				.withBinder(pst -> pst.setString(1, contractId))
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getOptionalResult();
	}

	 
	public List<PayrollRecord> findAll() {
		return new JdbcTemplate<PayrollRecord>()
				.forNamedSql( "TPAYROLLS_FINDALL" )
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getResultList();
	}

	 
	public Optional<PayrollRecord> findByContractIdAndDate(String id,
			LocalDate date) {
		return new JdbcTemplate< PayrollRecord >()
				.forNamedSql( "TPAYROLLS_FINDBYCONTRACTANDDATE" )
				.withBinder(pst -> {
					pst.setString(1, id);
					pst.setDate(2, Date.valueOf(date));
				})
				.withRowMapper( rs -> assembler.toRecord(rs) )
				.getOptionalResult();
	}

}
