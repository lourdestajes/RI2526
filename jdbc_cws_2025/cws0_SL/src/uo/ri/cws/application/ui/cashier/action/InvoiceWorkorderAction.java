package uo.ri.cws.application.ui.cashier.action;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.invoice.create.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoiceWorkorder;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class InvoiceWorkorderAction implements Action {

     
    public void execute() throws BusinessException {
        List<String> workOrderIds = new ArrayList<>();

        // Ask the user the work order ids
        do {
            String id = Console.readString("Workorder id");
            workOrderIds.add(id);
        } while (moreWorkOrders());

        InvoiceDto invoice = new InvoiceWorkorder(workOrderIds).execute();
        displayInvoice(invoice.number,
                invoice.date,
                invoice.amount,
                invoice.vat);

    }

    private boolean moreWorkOrders() {
        return Console.readString("more work orders? (y/n) ").equalsIgnoreCase("y");
    }


    private void displayInvoice(long numberInvoice,
            LocalDate dateInvoice,
            double totalInvoice,
            double vat) {

        Console.printf("Invoice number: %d\n", numberInvoice);
        Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dateInvoice);
        Console.printf("\tAmount: %.2f €\n", totalInvoice);
        Console.printf("\tVAT: %.1f %% \n", vat);
        double totalConIva = totalInvoice + vat;
        Console.printf("\tTotal (including VAT): %.2f €\n", totalConIva);
    }
}
