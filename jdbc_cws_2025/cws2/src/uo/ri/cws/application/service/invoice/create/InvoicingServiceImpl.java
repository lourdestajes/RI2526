package uo.ri.cws.application.service.invoice.create;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.command.CommandExecutor;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.commands.CreateInvoiceFor;
import uo.ri.cws.application.service.invoice.create.commands.FindInvoicesByVehiclePlate;
import uo.ri.cws.application.service.invoice.create.commands.FindNotInvoicedWorkOrdersByClientNif;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.NotYetImplementedException;


public class InvoicingServiceImpl implements InvoicingService {

	private CommandExecutor executor = new CommandExecutor();

	 
	public InvoiceDto create(List<String> woIds)
			throws BusinessException {

		return executor.execute( new CreateInvoiceFor( woIds) );
	}

	 
	public List<InvoicingWorkOrderDto> findWorkOrdersByClientNif(String nif)
			throws BusinessException {
		throw new NotYetImplementedException();
	}

	 
	public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientNif(
			String nif) throws BusinessException {
		return executor.execute( new FindNotInvoicedWorkOrdersByClientNif( nif ) );
	}

	 
	public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate)
			throws BusinessException {
		throw new NotYetImplementedException();
	}

	 
	public Optional<InvoiceDto> findInvoiceByNumber(Long number)
			throws BusinessException {
		throw new NotYetImplementedException();
	}

	 
	public List<PaymentMeanDto> findPayMeansByClientNif(String nif)
			throws BusinessException {
		throw new NotYetImplementedException();
	}

	 
	public void settleInvoice(String invoiceId, Map<String, Double> charges)
			throws BusinessException {
		throw new NotYetImplementedException();
	}


	@Override
	public List<InvoiceDto> findInvoicesByVehicle(String plate) throws BusinessException {
		return executor.execute(new FindInvoicesByVehiclePlate(plate));
	}
	
	
}
