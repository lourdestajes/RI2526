package uo.ri.cws.application.service.spare.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway;
import uo.ri.cws.application.persistence.sparePart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway.SubstitutionRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteSparePart implements Command<Void> {

	private String code;
	private SparePartGateway spg = Factories.persistence.forSparePart();
	private SupplyGateway sug = Factories.persistence.forSupplyGateway();
	private SubstitutionGateway sg = Factories.persistence.forSubstitutionsGateway();

	public DeleteSparePart(String code) {
		ArgumentChecks.isNotNull( code );
		this.code = code;
	}

	 
	public Void execute() throws BusinessException {
		Optional<SparePartRecord> os = spg.findByCode( code );
		BusinessChecks.exists(os, "There is not exist such Spare Part");
		
		SparePartRecord sp = os.get();
		checkCanBeDeleted(sp);

		spg.remove( sp.id );

		return null;
	}

	private void checkCanBeDeleted(SparePartRecord sp)
			throws BusinessException {
		
		List<SupplyRecord> osupply = sug.findBySparePartId( sp.id );
		BusinessChecks.isTrue(osupply.isEmpty(), "The spare part has supplies");
		
		List<SubstitutionRecord> osubs = sg.findBySparePartId( sp.id );
		BusinessChecks.isTrue( osubs.isEmpty(),	"The spare part has substitutions");
	}

}
