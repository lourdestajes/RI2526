package uo.ri.cws.application.service.invoice.create;

import java.time.LocalDate;

public class InvoiceDto {

    public String id; // the surrogate id (UUID)
    public long version;

    public double amount; // total amount (money) vat included
    public double vat; // amount of vat (money)
    public long number; // the invoice identity, a sequential number
    public LocalDate date; // of the invoice
    public String state; // the state as in InvoiceState
}
