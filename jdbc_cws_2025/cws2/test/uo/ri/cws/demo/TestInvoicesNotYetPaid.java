package uo.ri.cws.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uo.ri.conf.Factories;
import uo.ri.util.exception.BusinessException;

public class TestInvoicesNotYetPaid {
    @Test
    public void invoicesNotYetPaid() throws BusinessException {
	assertEquals(6709.71,
	    Factories.service.forCreateInvoiceService().findNotYetPaidAmount(),
	    2d);
    }
}
