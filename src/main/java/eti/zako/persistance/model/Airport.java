package eti.zako.persistance.model;

import eti.zako.persistance.PersistanceStore;
import org.garret.perst.Persistent;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Airport extends Persistent{

    public String airportId;
    public String city;
    public String icaoID;
    public String iataID;
    public Integer LAT;
    public Integer LON;

    public Airport() {

    }

    public Airport(String airportId, String city, String icaoID, String iataID, Integer LAT, Integer LON) {
        this.airportId = airportId;
        this.city = city;
        this.icaoID = icaoID;
        this.iataID = iataID;
        this.LAT = LAT;
        this.LON = LON;
    }

    @Override
    public void writeExternal(ObjectOutput out) {
        try {
            out.writeChars(airportId);
            out.writeChars(city);
            out.writeChars(icaoID);
            out.writeChars(iataID);
            out.writeInt(LAT);
            out.writeInt(LON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readExternal(ObjectInput in) {
        try {
            airportId = in.readObject().toString();
            city = in.readObject().toString();
            icaoID = in.readObject().toString();
            iataID = in.readObject().toString();
            LAT = in.readInt();
            LON = in.readInt();
        } catch (IOException  e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Airport parse(PersistanceStore store, JSONObject object) {
        Airport newAirport = new Airport();

        newAirport.airportId = object.get("airportID").toString();
        newAirport.city = object.get("city").toString();
        newAirport.icaoID = object.get("icaoID").toString();
        newAirport.iataID = object.get("iataID").toString();
        newAirport.LAT = Integer.parseInt(object.get("LAT").toString());
        newAirport.LON = Integer.parseInt(object.get("LON").toString());

        return newAirport;
    }

    public static JSONObject toJSON(Airport airport) {
        JSONObject object = new JSONObject();
        object.put("airportID", airport.airportId);
        object.put("city", airport.city);
        object.put("icaoID", airport.icaoID);
        object.put("iataID", airport.iataID);
        object.put("LAT", airport.LAT);
        object.put("LON", airport.LON);

        return object;
    }
}
