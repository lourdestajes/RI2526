package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersByClientAction implements Action {

    /**
     * Process: - Ask customer nif - Display all uncharged workorder (status <>
     * 'INVOICED'). For each workorder, display id, vehicle id, date, status,
     * amount and description
     */

     
    public void execute() throws BusinessException {
        InvoicingService service = Factories.service.forCreateInvoiceService();
        String nif = Console.readString("Client nif ");

        List<InvoicingWorkOrderDto> rs = service
                .findNotInvoicedWorkOrdersByClientNif(nif);
                
        Console.println("\nClient's not invoiced work orders\n");
        if (rs.isEmpty()) {
            Console.println("There are no work orders for this client");
            return;
        }
        for (InvoicingWorkOrderDto r : rs)
            Printer.printInvoicingWorkOrder(r);
    }

}
