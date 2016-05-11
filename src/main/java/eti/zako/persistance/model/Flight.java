package eti.zako.persistance.model;

import eti.zako.persistance.PersistanceStore;
import org.garret.perst.Persistent;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;import java.util.Date;

public class Flight extends Persistent {

    public Integer ID;
    public String flightNumber;
    public String airportID;
    public String destination;
    public Date date;
    public Integer freeSeats;
    public Integer ticketPrice;
    public Integer distance;

    public Flight() {

    }

    public Flight(Integer ID, String flightNumber, String airportID, String destination, Date date, Integer freeSeats, Integer ticketPrice, Integer distance) {
        this.ID = ID;
        this.flightNumber = flightNumber;
        this.airportID = airportID;
        this.destination = destination;
        this.date = date;
        this.freeSeats = freeSeats;
        this.ticketPrice = ticketPrice;
        this.distance = distance;
    }

    @Override
    public void writeExternal(ObjectOutput out) {
        try {
            out.writeInt(ID);
            out.writeChars(flightNumber);
            out.writeChars(airportID);
            out.writeChars(destination);
            out.writeObject(date);
            out.writeInt(freeSeats);
            out.writeInt(ticketPrice);
            out.writeInt(distance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readExternal(ObjectInput in) {
        try {
            ID = in.readInt();
            flightNumber = in.readObject().toString();
            airportID = in.readObject().toString();
            destination = in.readObject().toString();
            date = (Date) in.readObject();
            freeSeats = in.readInt();
            ticketPrice = in.readInt();
            distance = in.readInt();
        } catch (IOException  e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Flight parse(PersistanceStore store, JSONObject object) {
        Flight newFlight = new Flight();

        newFlight.ID = Integer.parseInt(object.get("ID").toString());
        newFlight.flightNumber = object.get("flightNumber").toString();
        newFlight.airportID = object.get("airportID").toString();
        newFlight.destination = object.get("destination").toString();
        newFlight.date = (Date) object.get("date");
        newFlight.freeSeats = Integer.parseInt(object.get("freeSeats").toString());
        newFlight.ticketPrice = Integer.parseInt(object.get("ticketPrice").toString());
        newFlight.distance = Integer.parseInt(object.get("distance").toString());

        return newFlight;
    }

    public static JSONObject toJSON(Flight flight) {
        JSONObject object = new JSONObject();
        object.put("ID", flight.ID);
        object.put("flightNumber", flight.flightNumber);
        object.put("airportID", flight.airportID);
        object.put("destination", flight.destination);
        object.put("date", flight.date);
        object.put("freeSeats", flight.freeSeats);
        object.put("ticketPrice", flight.ticketPrice);
        object.put("distance", flight.distance);

        return object;
    }

}
