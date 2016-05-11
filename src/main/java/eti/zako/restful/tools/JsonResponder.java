package eti.zako.restful.tools;

import eti.zako.persistance.PersistanceStore;
import eti.zako.persistance.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.util.ArrayList;

public class JsonResponder {

    public static String getOkResponse() {
        return "{\"response\":\"ok\"}";
    }

    public static String getErrorResponseWithMessage(String message) {
        return "{\"response\":\"" + message + "\"}";
    }

    public static String getListDatabases(String[] databases) {
        JSONObject response = new JSONObject();
        JSONArray array = new JSONArray();
        for (String database : databases) {
            array.add(database);
        }
        response.put("databases", array);
        return response.toJSONString();
    }

    public static String getExceptionMessage(Exception e) {
        JSONObject response = new JSONObject();
        response.put("error", "An error was found in databaseOperations.getBoundingRectangle");
        response.put("message", e.getMessage());
        return response.toJSONString();
    }

    public static String extractTstamp(String newNodes) {
        JSONObject newNodesContainer = (JSONObject) JSONValue.parse(newNodes);
        return (String) newNodesContainer.get("tstamp");
    }

    public static String getAllDates(ArrayList<String> allDates) {
        JSONObject allDatesJson = new JSONObject();
        JSONArray allDatesArray = new JSONArray();

        for (String s : allDates) {
            allDatesArray.add(s);
        }
        allDatesJson.put("allDates", allDatesArray);

        return allDatesJson.toJSONString();
    }

    public static ArrayList<String> getAllGivenDates(String givenDates) {
        JSONObject allGivenDates = (JSONObject) JSONValue.parse(givenDates);
        ArrayList<String> givenDatesArray = new ArrayList<String>();
        JSONArray array = (JSONArray) allGivenDates.get("dates");

        for (Object o : array.toArray()) {
            givenDatesArray.add((String) o);
        }

        return givenDatesArray;
    }

    public static JSONObject getAllData(PersistanceStore store) {
        PersistanceRoot root = (PersistanceRoot) store.getStorage().getRoot();
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for(Object o : root.indexAirport.toArray()) {
            if (o instanceof Airport) {
                array.add(Airport.toJSON((Airport) o));
            }
        }
        for(Object o : root.indexGate.toArray()) {
            if(o instanceof Gate) {
                array.add(Gate.toJSON((Gate) o));
            }
        }
        for(Object o : root.indexCKIN.toArray()) {
            if(o instanceof CKIN) {
                array.add(CKIN.toJSON((CKIN) o));
            }
        }
        for(Object o : root.indexFlight.toArray()) {
            if(o instanceof Flight) {
                array.add(Airport.toJSON((Airport) o));
            }
        }

        object.put("result", array);
        return object;
    }
}
