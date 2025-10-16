package uo.ri.cws.application.service.invoice.create;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.commands.FindNotInvoicedWorkOrdersByClient;
import uo.ri.cws.application.service.invoice.create.commands.InvoiceWorkorder;
import uo.ri.util.exception.BusinessException;

public class InvoicingServiceImpl implements InvoicingService {

     
    public InvoiceDto create(List<String> workOrderIds)
            throws BusinessException {
        return new InvoiceWorkorder(workOrderIds).execute();
    }

     
    public List<InvoicingWorkOrderDto> findWorkOrdersByClientNif(String nif)
            throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

     
    public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientNif(
            String nif) throws BusinessException {
        return new FindNotInvoicedWorkOrdersByClient(nif).execute();

    }

     
    public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate)
            throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

     
    public Optional<InvoiceDto> findInvoiceByNumber(Long number)
            throws BusinessException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

     
    public List<PaymentMeanDto> findPayMeansByClientNif(String nif)
            throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

     
    public void settleInvoice(String invoiceId, Map<String, Double> charges)
            throws BusinessException {
        // TODO Auto-generated method stub

    }

}
