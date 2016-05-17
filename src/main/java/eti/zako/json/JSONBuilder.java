package eti.zako.json;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.controller.HibernateController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eti.zako.sqlite.model.Airport;
import eti.zako.sqlite.model.CKIN;
import eti.zako.sqlite.model.FlightPath;
import eti.zako.sqlite.model.Flights;
import eti.zako.sqlite.model.Gate;

public class JSONBuilder {

	public static String prepareAutocompleteJSON(List<Airport> airports) {
		JsonObject autocomplete = new JsonObject();
    	JsonArray jArray = new JsonArray();
    	String resultJson = null;
    	for(Airport element : airports) {
    		jArray.add(element.getCity());
    	}
    	autocomplete.add("autocomplete", jArray);
    	resultJson = autocomplete.toString();
    	return resultJson;
    }
    
    public static String prepareMarkersJSON(List<Airport> airports) {
        String resultJson = null;
        JsonObject markers = new JsonObject();
        JsonArray jArray = new JsonArray();
        
        for(Airport airport : airports) {
            JsonObject element  = new JsonObject();
            element.addProperty("city", airport.getCity());
            element.addProperty("lon", airport.getLON());
            element.addProperty("lat", airport.getLAT());
            jArray.add(element);
        }
        markers.add("markers", jArray);
        resultJson = markers.toString();
        return resultJson;
    }
    
    public static JsonArray preparePathJson(FlightPath path, Date startDate) {
        JsonArray pathArray = new JsonArray();
        List<Flights> flights = new ArrayList<Flights>();
        List<Gate> gates = new ArrayList<>();
        Airport srcAirport = null, destAirport = null;
        List<CKIN> ckins = new ArrayList<>();
        String[] airportIDs = path.getPathString().split("\\.");
        try {
            for(int i = 0; i < airportIDs.length - 1; i++) {
                if(!airportIDs[i].equals("") && !airportIDs[i+1].equals("")) {
                    JsonObject singleFlight = new JsonObject();
                    JsonObject flightData = new JsonObject();
                    JsonArray gateData = new JsonArray();
                    JsonArray ckinData = new JsonArray();
                    JsonObject srcAirportJson = new JsonObject();
                    JsonObject destAirportJson = new JsonObject();
                    flights = HibernateController.getDataList("Flights",
                        "AirportID='" + airportIDs[i] + "' and destination='" + airportIDs[i+1] + "'");
                    Flights closestFlight = findClosestFlight(flights, startDate);
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(closestFlight.getDate());
                    srcAirport = HibernateController.<Airport>getSingleElement("Airport", "airportId=" + closestFlight.getAirportID());
                    destAirport = HibernateController.<Airport>getSingleElement("Airport", "airportId=" + closestFlight.getDestination());
                    gates = HibernateController.<Gate>getDataList("Gate", "ID=" + closestFlight.getID());
                    ckins = HibernateController.<CKIN>getDataList("CKIN", "ID=" + closestFlight.getID());
                    
                    srcAirportJson.addProperty("lat", srcAirport.getLAT());
                    srcAirportJson.addProperty("lon", srcAirport.getLON());
                    srcAirportJson.addProperty("city", srcAirport.getCity());
                    destAirportJson.addProperty("lat", destAirport.getLAT());
                    destAirportJson.addProperty("lon", destAirport.getLON());
                    destAirportJson.addProperty("city", destAirport.getCity());
                    ckinData = parseCkins(ckins);
                    gateData = parseGates(gates);
                    flightData.addProperty("flightNumber", closestFlight.getFlightNumber());
                    flightData.addProperty("date", String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
                    flightData.addProperty("freeSeats", closestFlight.getFreeSeats());
                    flightData.addProperty("ticketPrice", closestFlight.getTicketPrice());
                    flightData.addProperty("distance", closestFlight.getDistance());
                    flightData.addProperty("length", parseFlightDuration(closestFlight.getDuration()));
                    
                    singleFlight.add("dest", destAirportJson);
                    singleFlight.add("src", srcAirportJson);
                    singleFlight.add("ckin", ckinData);
                    singleFlight.add("gate", gateData);
                    singleFlight.add("flight", flightData);
                    
                    pathArray.add(singleFlight);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return pathArray;
    }
    
    public static String mergePaths(List<JsonArray> pathJsons) {
        String resultJson = null;
        JsonArray jArray = new JsonArray();
        JsonObject root = new JsonObject();
        for(JsonArray pathJson : pathJsons) {
            jArray.add(pathJson);
        }
        root.add("path", jArray);
        resultJson = root.toString();
        return resultJson;
    }
	
	private static Flights findClosestFlight(List<Flights> flights, Date currentDate) {
	    Flights closestFlight = null;
	    long minDiff = Long.MAX_VALUE;
	    long currentDiff = 0;
	    for(Flights f : flights) {
	        currentDiff = f.getDate().getTime() - currentDate.getTime();
	        if(currentDiff < minDiff && currentDiff > 0) {
	            closestFlight = f;
	            minDiff = currentDiff;
	        }
	    }
	    return closestFlight;
	}
	
	private static JsonArray parseCkins(List<CKIN> ckins) {
	    JsonArray jArray = new JsonArray();
	    Calendar calendar = GregorianCalendar.getInstance();
	    for(CKIN ckin : ckins) {
	       JsonObject singleCkin = new JsonObject();
	       calendar.setTime(ckin.getTimeStart());
	       singleCkin.addProperty("timeStart", String.format("%d:%02d", calendar.get(Calendar.HOUR_OF_DAY ), calendar.get(Calendar.MINUTE)));
	       calendar.setTime(ckin.getTimeStop());
	       singleCkin.addProperty("timeStop", String.format("%d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
	       singleCkin.addProperty("luggageLimit", ckin.getLuggageLimit());
	       jArray.add(singleCkin);
	    }
	    return jArray;
	}
	
	private static JsonArray parseGates(List<Gate> gates) {
	    JsonArray jArray = new JsonArray();
	    Calendar calendar = Calendar.getInstance();
	    for(Gate gate : gates) {
	       JsonObject singleGate = new JsonObject();
	       calendar.setTime(gate.getTimeStart());
	       singleGate.addProperty("timeStart", String.format("%d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
	       calendar.setTime(gate.getTimeStop());
	       singleGate.addProperty("timeStop", String.format("%d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
	       jArray.add(singleGate);
	    }
	    return jArray;
	}
	
	private static String parseFlightDuration(Double duration) {
	    Integer dur = (int)Math.round(duration*60);
	    String durationStr = null;
	    int hours = dur / 60;
	    int minutes = dur % 60;
	    durationStr = String.format("%d:%02d", hours, minutes);
	    return durationStr;
	}
}
