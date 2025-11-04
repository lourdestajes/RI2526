package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindNotInvoicedWorkOrdersByClientNif implements Command<List<InvoicingWorkOrderDto>> {

	private String nif;
	private WorkOrderRepository workOrderRepo = Factories.repository.forWorkOrder();

	public FindNotInvoicedWorkOrdersByClientNif(String nif) {
		ArgumentChecks.isNotNull(nif, "The client NIF cannot be null");
		this.nif = nif;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		return DtoAssembler.toInvoicingWorkOrderDtoList(workOrderRepo.findNotInvoicedByClientNif(nif));
	}
}
