package eti.zako.sqlite.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="airport")
public class Airport {

    private Integer airportId;
    private String city;
    private String icaoID;
    private String iataID;
    private String LAT;
    private String LON;

    public Airport() {
    	
    }
    
    public Airport(Integer airportId, String city, String icaoID, String iataID, String LAT, String LON) {
        this.airportId = airportId;
        this.city = city;
        this.icaoID = icaoID;
        this.iataID = iataID;
        this.LAT = LAT;
        this.LON = LON;
    }

    @Id
	public Integer getAirportId() {
		return airportId;
	}

	public void setAirportId(Integer airportId) {
		this.airportId = airportId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIcaoID() {
		return icaoID;
	}

	public void setIcaoID(String icaoID) {
		this.icaoID = icaoID;
	}

	public String getIataID() {
		return iataID;
	}

	public void setIataID(String iataID) {
		this.iataID = iataID;
	}

	public String getLAT() {
		return LAT;
	}

	public void setLAT(String lAT) {
		LAT = lAT;
	}
	
	public String getLON() {
		return LON;
	}

	public void setLON(String lON) {
		LON = lON;
	}
}
