package uo.ri.cws.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name="TVOUCHERS")
public class Voucher extends PaymentMean {
	@Column(unique=true) private String code;
	private double available = 0.0;
	private String description;

	Voucher() {
		// for JPA
	}
	
	public Voucher(String code, String description, double available) {
		ArgumentChecks.isNotBlank(code, "The code cannot be null");
		ArgumentChecks.isNotBlank(description, "The description cannot be null");
		ArgumentChecks.isTrue(available >= 0.0, "The available amount must be positive");
		
		this.code = code;
		this.available = available;
		this.description = description;
	}
	
	
	public String getCode() {
		return code;
	}


	public double getAvailable() {
		return available;
	}


	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available + ", description=" + description + "]";
	}


	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		ArgumentChecks.isNotNull(amount, "The amount cannot be null");
		ArgumentChecks.isTrue(amount >= 0.0, "The amount must be positive");
		StateChecks.isTrue(canPay(amount), "Not enough available to pay the amount");
		super.pay(amount);
		available -= amount;		
	}

	/**
	 * A voucher can pay if it has enough available to pay the amount
	 */
	@Override
	public boolean canPay(Double amount) {
		ArgumentChecks.isNotNull(amount, "The amount cannot be null");
		ArgumentChecks.isTrue(amount >= 0.0, "The amount must be positive");
		return this.available >= amount;
	}

}

