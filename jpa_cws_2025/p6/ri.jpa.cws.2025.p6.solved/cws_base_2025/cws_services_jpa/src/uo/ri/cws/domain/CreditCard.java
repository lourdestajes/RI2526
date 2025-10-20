package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class CreditCard extends PaymentMean {
	private String number;
	private String type;
	private LocalDate validThru;
	
	
	public CreditCard(String num, String type, LocalDate date) {
		ArgumentChecks.isNotBlank(num, "The number cannot be null");
		ArgumentChecks.isNotBlank(type, "The type cannot be null");
		ArgumentChecks.isNotNull(date, "The date cannot be null");
		this.number = num;
		this.type = type;
		this.validThru = date;
	}


	public String getNumber() {
		return number;
	}


	public String getType() {
		return type;
	}


	public LocalDate getValidThru() {
		return validThru;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(number);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number);
	}


	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type + ", validThru=" + validThru + "]";
	}


	@Override
	public void pay(double amount) {
		ArgumentChecks.isTrue(amount > 0, "The amount must be positive");
		StateChecks.isTrue( canPay(amount), "The credit card is outdated");
		super.pay(amount);
	}
	/**
	 * A credit card can pay if is not outdated 
	 */
	@Override
	public boolean canPay(Double amount) {
		ArgumentChecks.isNotNull(amount, "The amount cannot be null");
		ArgumentChecks.isTrue(amount > 0, "The amount must be positive");
		return validThru.isAfter( LocalDate.now() );
	}

}

