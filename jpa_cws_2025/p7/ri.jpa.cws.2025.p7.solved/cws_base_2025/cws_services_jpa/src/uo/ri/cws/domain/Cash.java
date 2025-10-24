package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TCASHES")
public class Cash extends PaymentMean {

	Cash() {
		// for JPA
	}
	
	public Cash(Client client) {
		ArgumentChecks.isNotNull(client, "The client cannot be null");
		Associations.Holds.link(this, client);
	}

	/**
	 * A cash can always pay
	 */
	@Override
	public boolean canPay(Double amount) {
		return true;
	}

	@Override
	public String toString() {
		return "Cash [getClient()=" + getClient() + "]";
	}

}
