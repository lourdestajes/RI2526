package uo.ri.cws.application.service.invoice;

import java.time.LocalDateTime;

public class InvoicingWorkOrderDto {
    public String id;
    public String description;
    public LocalDateTime date;
    public String state;
    public double amount;
}