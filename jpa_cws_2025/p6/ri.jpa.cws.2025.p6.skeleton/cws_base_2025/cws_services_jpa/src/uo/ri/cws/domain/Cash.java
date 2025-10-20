package uo.ri.cws.domain;

public class Cash extends PaymentMean {

	
	/**
	 * A cash can always pay
	 */
	@Override
	public boolean canPay(Double amount) {
		return true;
	}


}
