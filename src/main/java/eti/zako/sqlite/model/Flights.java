package eti.zako.sqlite.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="flights")
public class Flights {
	
	private Integer ID;
    private String flightNumber;
    private Integer airportID;
    private Integer destination;
    private Date date;
    private Integer freeSeats;
    private Integer ticketPrice;
    private Integer distance;
    private Double duration;
    
    public Flights() {
    }


    public Flights(Integer ID, String flightNumber, Integer airportID, Integer destination, Date date, Integer freeSeats, Integer ticketPrice, Integer distance, Double duration) {
        this.ID = ID;
        this.flightNumber = flightNumber;
        this.airportID = airportID;
        this.destination = destination;
        this.date = date;
        this.freeSeats = freeSeats;
        this.ticketPrice = ticketPrice;
        this.distance = distance;
        this.duration = duration;
    }


    @Id
	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}


	public String getFlightNumber() {
		return flightNumber;
	}


	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}


	public Integer getAirportID() {
		return airportID;
	}


	public void setAirportID(Integer airportID) {
		this.airportID = airportID;
	}


	public Integer getDestination() {
		return destination;
	}


	public void setDestination(Integer destination) {
		this.destination = destination;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Integer getFreeSeats() {
		return freeSeats;
	}


	public void setFreeSeats(Integer freeSeats) {
		this.freeSeats = freeSeats;
	}


	public Integer getTicketPrice() {
		return ticketPrice;
	}


	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}


	public Integer getDistance() {
		return distance;
	}


	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	
	public void setDuration(Double duration) {
	    this.duration = duration;
	}
	
	/**
	 * zwraca dlugosc lotu w minutach
	 */
	public Double getDuration() {
	    return this.duration;
	}
}
