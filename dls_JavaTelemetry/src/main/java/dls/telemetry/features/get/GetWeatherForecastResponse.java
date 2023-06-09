package dls.telemetry.features.get;

import java.time.LocalDate;

public class GetWeatherForecastResponse {

	public String city;
	public String address;
	public LocalDate date;
	public int temperatureC;
	public int temperatureF;
	public String summary;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getTemperatureC() {
		return temperatureC;
	}
	public void setTemperatureC(int temperatureC) {
		this.temperatureC = temperatureC;
		this.temperatureF = 32 + (int)(this.temperatureC / 0.5556);
	}
	public int getTemperatureF() {
		return temperatureF;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
