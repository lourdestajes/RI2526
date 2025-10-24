package uo.ri.cws.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.exception.BusinessException;

public class TestFindBaseWageGreaterThan {

    private List<String> ids;
    private MechanicCrudService mcs = Factories.service
	.forMechanicCrudService();

    @Before
    public void setUp() throws Exception {

	ids = loadIds();
    }

    private List<String> loadIds() {
	return List.of("229c9aa9-8f54-439a-82c9-fea5ece2789c",
	    "7096eab0-e4ad-4cc6-bed0-17ceffed56ad",
	    "42171f9b-091b-4446-917e-c08725d615ec",
	    "009aed97-4639-4f0a-b818-d21000f830f2");
    }

    @Test
    public void testNegativeParameter() throws BusinessException {
	assertThrows(IllegalArgumentException.class, () -> {
	    mcs.findBaseWageGreaterThan(-4);
	});

    }

    @Test
    public void test() throws BusinessException {

	List<MechanicDto> mechanics = mcs.findBaseWageGreaterThan(30000.0);

	assertEquals(ids.size(), mechanics.size());
	for (MechanicDto dto : mechanics) {
	    assertTrue(ids.contains(dto.id));
	}
    }
}
