package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private List<String> workOrderIds;
    private WorkOrderRepository wrkrsRepo = Factories.repository
        .forWorkOrder ( );
    private InvoiceRepository invsRepo = Factories.repository.forInvoice ( );

    public CreateInvoiceFor ( List<String> workOrderIds ) {
        ArgumentChecks.isNotNull ( workOrderIds );
        ArgumentChecks.isFalse ( workOrderIds.isEmpty ( ) );
        ArgumentChecks.isFalse ( workOrderIds.stream ( )
            .anyMatch ( i -> i == null ) );

        this.workOrderIds = workOrderIds;
    }

    @Override
    public InvoiceDto execute ( ) throws BusinessException {
        List<WorkOrder> workOrders = wrkrsRepo.findByIds ( workOrderIds );
        assertAllWorkOrdersExist ( workOrders );
        assertAllWorkOrdersFinished ( workOrders );
        long numberInvoice = generateInvoiceNumber ( );
        Invoice result = new Invoice ( numberInvoice, workOrders );
        return DtoAssembler.toDto ( result );
    }

    /**
     * checks whether every work order exist
     */
    private void assertAllWorkOrdersExist ( List<WorkOrder> wos )
            throws BusinessException {
        BusinessChecks.isTrue ( wos.size ( ) == workOrderIds.size ( ),
                "Some workorder does not exist" );
    }

    /**
     * checks whether every work order id is FINISHED
     */
    private void assertAllWorkOrdersFinished ( List<WorkOrder> wos )
            throws BusinessException {
        BusinessChecks.isTrue ( wos.stream ( )
            .allMatch ( wo -> wo.isFinished ( ) ),
                "Some workorder is not finished" );
    }

    /**
     * Generates next invoice number (not to be confused with the inner id)
     */
    private long generateInvoiceNumber ( ) {
        return invsRepo.getNextInvoiceNumber ( );
    }

}
