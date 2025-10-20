package uo.ri.cws.domain;

import java.util.Objects;

import uo.ri.util.assertion.ArgumentChecks;

/**
 * This class is a Value Type, thus
 *    - no setters
 *	  - hashcode and equals over all attributes
 */
public class Address {
	private String street;
	private String city;
	private String zipCode;

	public Address(String street, String city, String zipCode) {
		ArgumentChecks.isNotBlank(street, "Street cannot be blank");
		ArgumentChecks.isNotBlank(city, "City cannot be blank");
		ArgumentChecks.isNotBlank(zipCode, "Zip code cannot be blank");

		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(city, street, zipCode);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(street, other.street)
				&& Objects.equals(zipCode, other.zipCode);
	}
	
	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", zipCode=" + zipCode + "]";
	}
}
