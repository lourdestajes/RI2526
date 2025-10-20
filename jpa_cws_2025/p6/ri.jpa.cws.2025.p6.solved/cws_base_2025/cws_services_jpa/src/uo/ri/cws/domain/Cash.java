package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Cash extends PaymentMean {

	
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
