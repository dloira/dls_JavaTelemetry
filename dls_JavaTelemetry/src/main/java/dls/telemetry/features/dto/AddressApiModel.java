package dls.telemetry.features.dto;

import java.io.Serializable;

public class AddressApiModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String city;
	public String street_name;
	public String street_address;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet_name() {
		return street_name;
	}
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	public String getStreet_address() {
		return street_address;
	}
	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}
}
