package uo.ri.cws.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.exception.BusinessException;

public class TestCalculateTimeOnInterventions {

    private static int time = 9437;
    private MechanicCrudService mcs = Factories.service
	.forMechanicCrudService();

    @Test
    void testEmptyId() {
	assertThrows(IllegalArgumentException.class, () -> {
	    mcs.calculateTimeOnInterventions("");
	});
    }

    @Test
    void testNullId() {
	assertThrows(IllegalArgumentException.class, () -> {
	    mcs.calculateTimeOnInterventions(null);
	});
    }

    @Test
    void testNonId() {
	assertThrows(BusinessException.class, () -> {
	    mcs.calculateTimeOnInterventions(UUID.randomUUID().toString());
	});
    }

    @Test
    public void test() throws BusinessException {

	int res = mcs.calculateTimeOnInterventions(
	    "67b33ab9-99b8-4c27-95ab-f82d09b688ae");
	assertTrue(time == res);
    }

}
