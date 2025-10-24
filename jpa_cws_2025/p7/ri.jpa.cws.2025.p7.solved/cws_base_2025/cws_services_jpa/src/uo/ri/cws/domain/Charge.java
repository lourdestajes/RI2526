package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name="TCHARGES",
	uniqueConstraints= {
			@UniqueConstraint(columnNames= {"invoice_id", "paymentmean_id"})
	}
)
public class Charge extends BaseEntity {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	@ManyToOne private Invoice invoice;
	@ManyToOne private PaymentMean paymentMean;

	Charge() {
		// for JPA
	}
	
	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		ArgumentChecks.isNotNull(invoice, "The invoice cannot be null");
		ArgumentChecks.isNotNull(paymentMean, "The payment mean cannot be null");
		ArgumentChecks.isTrue(amount >= 0.0, "The amount must be positive");
		
		// store the amount
		this.amount = amount;
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		paymentMean.pay( amount );
		// link invoice, this and paymentMean
		Associations.Settles.link( invoice, this, paymentMean );
	}
	

	public double getAmount() {
		return amount;
	}




	public Invoice getInvoice() {
		return invoice;
	}




	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		StateChecks.isFalse( invoice.isSettled(), 
			"The invoice is already settled" );
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		paymentMean.pay( -amount );
		// unlinks invoice, this and paymentMean
		Associations.Settles.unlink(this);
	}

	@Override
	public String toString() {
		return "Charge [amount=" + amount + ", invoice=" + invoice + ", paymentMean=" + paymentMean + "]";
	}

}
