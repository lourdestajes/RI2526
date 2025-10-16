package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.InvoiceAssembler;
import uo.ri.util.assertion.ArgumentChecks;

public class FindNotInvoicedWorkOrdersByClient {

	private String nif;

    public FindNotInvoicedWorkOrdersByClient(String nif) {
        ArgumentChecks.isNotEmpty(nif, "NIF cannot be null or empty");
        this.nif = nif;
    }

    public List<InvoicingWorkOrderDto> execute() {
        List<WorkorderRecord> result = new WorkOrderGatewayImpl().findNotInvoicedByClientNif(nif);
        return InvoiceAssembler.toInvoicingWorkOrderDtoList(result);
    }

}