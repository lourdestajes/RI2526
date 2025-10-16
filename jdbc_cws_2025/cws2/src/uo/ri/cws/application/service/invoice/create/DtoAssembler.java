package uo.ri.cws.application.service.invoice.create;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class DtoAssembler {

	public InvoiceDto toDto(InvoiceRecord arg) {
		InvoiceDto result = new InvoiceDto();
		result.id = arg.id;
		result.version = arg.version;
		
		result.number = arg.number;
		result.state = arg.state;
		result.date = arg.date;
		result.amount = arg.amount;
		result.vat = arg.vat;
		return result;
	}

	public List<InvoicingWorkOrderDto> toInvoicingWorkOrderList(
			List<WorkOrderRecord> arg) {
		List<InvoicingWorkOrderDto> result = new ArrayList<>();
		for (WorkOrderRecord record : arg) {
			result.add(toDto(record));
		}
		return result;
	}

	private InvoicingWorkOrderDto toDto(WorkOrderRecord record) {
		InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
		dto.id = record.id;
		dto.date = record.date;
		dto.description = record.description;
		dto.date = record.date;
		dto.state = record.state;
		dto.amount = record.amount;
		return dto;
	}

}
