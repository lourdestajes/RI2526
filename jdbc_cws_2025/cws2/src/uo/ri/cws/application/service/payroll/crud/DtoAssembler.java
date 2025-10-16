package uo.ri.cws.application.service.payroll.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.util.math.Rounds;

public class DtoAssembler {

	public PayrollDto toDto(PayrollRecord arg) {
		PayrollDto result = new PayrollDto();

		result.id = arg.id;
		result.version = arg.version;
		result.date = arg.date;

		result.baseSalary = arg.baseSalary;
		result.extraSalary = arg.extraSalary;
		result.productivityEarning = arg.productivityEarning;
		result.trienniumEarning = arg.trienniumEarning;
		result.nicDeduction = arg.nicDeduction;
		result.taxDeduction = arg.taxDeduction;
		
		result.grossSalary = computeGrossSalary(arg);
		result.totalDeductions = computeTotalDeductions(arg);
		result.netSalary = Rounds.toCents( computeNetSalary(result) );

		result.contractId = arg.contract_id;
		return result;

	}

	private double computeNetSalary(PayrollDto result) {
		return result.grossSalary - result.totalDeductions;
	}

	private double computeTotalDeductions(PayrollRecord arg) {
		return arg.taxDeduction + arg.nicDeduction;
	}

	private double computeGrossSalary(PayrollRecord arg) {
		return arg.baseSalary + arg.extraSalary + arg.productivityEarning + arg.trienniumEarning;
	}
	
	public List<PayrollDto> toDtoList(List<PayrollRecord> all) {
		List<PayrollDto> result = new ArrayList<>();
		for (PayrollRecord record : all) {
			result.add(toDto(record));
		}
		return result;
	}


	public PayrollSummaryDto toSummaryDto(PayrollRecord arg) {
		PayrollSummaryDto result = new PayrollSummaryDto();
		result.id = arg.id;
		result.date = arg.date;
		
		double grossSalary = computeGrossSalary(arg);
		double totalDeductions = computeTotalDeductions(arg);
		result.netSalary = Rounds.toCents( grossSalary - totalDeductions );
		return result;
	}

	public List<PayrollSummaryDto> toSummaryDtoList(List<PayrollRecord> all) {
		List<PayrollSummaryDto> result = new ArrayList<>();
		for (PayrollRecord record : all) {
			result.add(toSummaryDto(record));
		}
		return result;
	}

}
