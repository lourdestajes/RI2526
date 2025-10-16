package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindNotInvoicedWorkOrdersByClientNif
		implements Command<List<InvoicingWorkOrderDto>> {

	private String nif;
	private WorkOrderGateway woGateway = Factories.persistence.forWorkOrder();
	private DtoAssembler assembler = new DtoAssembler();

	public FindNotInvoicedWorkOrdersByClientNif(String nif) {
		ArgumentChecks.isNotNull(nif);
		this.nif = nif;
	}

	 
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		List<WorkOrderRecord> records = woGateway.findNotInvoicedByClientNif(nif);
		return assembler.toInvoicingWorkOrderList( records );
	}

}
