package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindInvoicesByVehiclePlate implements Command<List<InvoiceDto>> {

    private String plate;

    public FindInvoicesByVehiclePlate(String plate) {
        ArgumentChecks.isNotBlank(plate, "Invalid plate");
        this.plate = plate;
    }

    @Override
    public List<InvoiceDto> execute() throws BusinessException {
        Optional<VehicleRecord> opt = Factories.persistence.forVehicle()
                                                           .findByPlate(plate);
        BusinessChecks.exists(opt, "Vehicle does not exist");
        List<InvoiceRecord> invoices = Factories.persistence.forInvoice()
                                                            .findByVehicleId(
                                                                    opt.get().id);
        return DtoAssembler.toDtoList(invoices);
    }

}
