package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class FindUnpaidAmount implements Command<Double> {

    @Override
    public Double execute() throws BusinessException {
        List<InvoiceRecord> invoices = Factories.persistence.forInvoice()
                                                            .findUnapidInvoices();
        double x = invoices.stream()
                           .mapToDouble(i -> i.amount)
                           .sum();
        return x;
    }

}
