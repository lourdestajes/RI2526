package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import uo.ri.cws.application.service.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.FindNotInvoicedWorkOrdersByClient;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersByClientAction implements Action {


    /**
     * Process: 
     * - Ask customer nif 
     * - Display all uncharged workorder (status <> 'INVOICED'). 
     * For each workorder, display id, vehicle id, date, status, amount 
     * and description
     */

     
    public void execute() throws BusinessException {
        String nif = Console.readString("Client nif ");

        List<InvoicingWorkOrderDto> rs = new FindNotInvoicedWorkOrdersByClient(
                nif).execute();
        Console.println("\nClient's not invoiced work orders\n");
        if (rs.isEmpty()) {
            Console.println("There are no work orders for this client");
            return;
        }
        for (InvoicingWorkOrderDto r : rs)
            printWorkOrder(r);
    }

    private void printWorkOrder(InvoicingWorkOrderDto rs) {
        Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n",
                rs.id,
                rs.description,
                rs.date,
                rs.state,
                rs.amount);
    }
}
