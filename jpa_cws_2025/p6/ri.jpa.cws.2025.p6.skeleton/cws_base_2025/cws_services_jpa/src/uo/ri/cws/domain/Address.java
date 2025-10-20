package uo.ri.cws.domain;

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

}
