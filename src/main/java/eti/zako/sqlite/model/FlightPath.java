package eti.zako.sqlite.model;

public class FlightPath {
    
    private Integer airportID;
    private Integer destination;
    private String pathString;
    
    public Integer getAirportID() {
        return airportID;
    }
    
    public Integer getDestination() {
        return destination;
    }
    public String getPathString() {
        return pathString;
    }
}
