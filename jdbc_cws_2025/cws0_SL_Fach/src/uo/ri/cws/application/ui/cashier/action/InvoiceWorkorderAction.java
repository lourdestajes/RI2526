package uo.ri.cws.application.ui.cashier.action;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class InvoiceWorkorderAction implements Action {

     
    public void execute() throws BusinessException {
        List<String> workOrderIds = new ArrayList<>();
        InvoicingService service = new InvoicingServiceImpl();

        // Ask the user the work order ids
        do {
            String id = Console.readString("Workorder id");
            workOrderIds.add(id);
        } while (moreWorkOrders());

        InvoiceDto invoice = service.create(workOrderIds);
        Printer.printInvoice(invoice);

    }

    private boolean moreWorkOrders() {
        return Console.readString("more work orders? (y/n) ").equalsIgnoreCase("y");
    }

}
