package uo.ri.cws.application.service.invoice.create.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.InvoiceAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.jdbc.Jdbc;

public class FindNotInvoicedWorkOrdersByClient {

	private String nif;

    public FindNotInvoicedWorkOrdersByClient(String nif) {
        ArgumentChecks.isNotEmpty(nif, "NIF cannot be null or empty");
        this.nif = nif;
    }

    public List<InvoicingWorkOrderDto> execute() {
    	List<InvoicingWorkOrderDto> list = new ArrayList<>();
    	try (Connection c = Jdbc.createThreadConnection()){
        	try  {
        		c.setAutoCommit(false);
                List<WorkorderRecord> result = new WorkOrderGatewayImpl().findNotInvoicedByClientNif(nif);
                list = InvoiceAssembler.toInvoicingWorkOrderDtoList(result);
                c.commit();
        	} catch (Exception e) {
				c.rollback();
				throw e;
			}
    	} catch (SQLException e) {
    		throw new RuntimeException("Error finding work orders for client with NIF: " + nif, e);
    	}
		return list;
    }

}