package uo.ri.cws.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.exception.BusinessException;

public class TestFindInvoicesByVehicle {

    private List<String> ids;
    private InvoicingService is = Factories.service.forCreateInvoiceService();

    @Before
    public void setUp() throws Exception {

        ids = loadIds();
    }

    private List<String> loadIds() {
        return List.of("937fc289-e6be-4d46-8a29-9e406d781d8d",
                "e2f8942c-6f16-4aca-81d4-981ac2b645cd");
    }

    @Test(expected = BusinessException.class)
    public void testNonExistent() throws BusinessException {

        is.findInvoicesByVehicle("non-existent-plate");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArguments() throws BusinessException {

        is.findInvoicesByVehicle(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyArguments() throws BusinessException {

        is.findInvoicesByVehicle("");

    }

    @Test
    public void test() throws BusinessException {

        List<InvoiceDto> invoices = is.findInvoicesByVehicle("2219-FAU");

        assertEquals(ids.size(), invoices.size());
        for (InvoiceDto dto : invoices) {
            assertTrue(ids.contains(dto.id));
        }
    }
}
