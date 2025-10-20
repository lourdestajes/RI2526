package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Client {
	private String nif;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;

	// Atributos accidentales
	private Set<Vehicle> vehicles = new HashSet<>();
	private Set<PaymentMean> paymentMeans = new HashSet<>();
	
	public Client(String nif, String name, String surname, String email, String phone, Address address) {
		ArgumentChecks.isNotBlank(nif, "NIF cannot be blank");
		ArgumentChecks.isNotBlank(name, "Name cannot be blank");
		ArgumentChecks.isNotBlank(surname, "Surname cannot be blank");
		ArgumentChecks.isNotBlank(email, "Email cannot be blank");
		ArgumentChecks.isNotBlank(phone, "Phone cannot be blank");
		ArgumentChecks.isNotNull(address, "Address cannot be null");
		
		this.nif = nif;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	
	public Client(String nif, String name, String surname) {
		this(nif, name, surname, "no@email", "no-phone", new Address("no-street", "no-city", "no-zip"));
	}


	public String getNif() {
		return nif;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public Address getAddress() {
		return address;
	}
	
	
    public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);
	}

	@Override
	public String toString() {
		return "Client [nif=" + nif + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone="
				+ phone + ", address=" + address + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(nif);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(nif, other.nif);
	}


	Set<Vehicle> _getVehicles() {
		return vehicles;
	}
    
	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}
    
}

