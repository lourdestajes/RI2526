package uo.ri.cws.application.service.invoice.create;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

public class InvoiceAssembler {

	public static InvoiceDto toDto(InvoiceRecord ir) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = ir.id;
		dto.number = ir.number;
		dto.date = ir.date;
		dto.amount = ir.amount;
		dto.vat = ir.vat;
		dto.amount = ir.amount;
		dto.state = ir.status;
		dto.version = ir.version;
		return dto;
	}
	
	public static List<InvoicingWorkOrderDto> toInvoicingWorkOrderDtoList(List<WorkorderRecord> arg) {
		List<InvoicingWorkOrderDto> result = new ArrayList<>();
		for (WorkorderRecord wr : arg) {
			InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
			dto.id = wr.id;
			dto.description = wr.description;
			dto.date = wr.date;
			dto.amount = wr.amount;
			dto.state = wr.state;
			result.add(dto);
		}
		return result;
	}

}
