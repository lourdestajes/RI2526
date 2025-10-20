package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class SparePart {
	// natural attributes
	private String code;
	private String description;
	private double price;
	private int stock;
	private int minStock;
	private int maxStock;

	// accidental attributes
	private Set<Substitution> substitutions = new HashSet<>();

	public SparePart(String code, String description, double price, int stock, int minStock, int maxStock) {
		ArgumentChecks.isNotBlank(code, "Code cannot be null or blank");
		ArgumentChecks.isNotBlank(description, "Description cannot be null or blank");
		ArgumentChecks.isTrue(price >= 0, "Price cannot be negative");
		ArgumentChecks.isTrue(stock >= 0, "Stock cannot be negative");
		ArgumentChecks.isTrue(minStock >= 0, "Min stock cannot be negative");
		ArgumentChecks.isTrue(maxStock >= minStock, "Max stock cannot be less than min stock");
		
		this.code = code;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.minStock = minStock;
		this.maxStock = maxStock;
	}

	public SparePart(String code, String description, double price) {
		this(code, description, price, 0, 0, 0);
	}

	public SparePart(String code) {
		this(code, "no-description", 0.0, 0, 0, 0);
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public int getMinStock() {
		return minStock;
	}

	public int getMaxStock() {
		return maxStock;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description + ", price=" + price + ", stock=" + stock
				+ ", minStock=" + minStock + ", maxStock=" + maxStock + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SparePart other = (SparePart) obj;
		return Objects.equals(code, other.code);
	}

	
}
