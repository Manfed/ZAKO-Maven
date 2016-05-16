package eti.zako.json;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eti.zako.sqlite.model.Airport;

public class JSONBuilder {

	public static String prepareAutocompleteJSON(List<Object> airports) {
		JsonObject autocomplete = new JsonObject();
    	JsonArray jArray = new JsonArray();
    	String resultJson = null;
    	for(Object element : airports) {
    		jArray.add(((Airport)element).getCity());
    	}
    	autocomplete.add("autocomplete", jArray);
    	resultJson = autocomplete.toString();
    	return resultJson;
    }
    
    public static String prepareMarkersJSON(List<Object> airports) {
        String resultJson = null;
        JsonObject markers = new JsonObject();
        JsonArray jArray = new JsonArray();
        
        for(Object airport : airports) {
            JsonObject element  = new JsonObject();
            element.addProperty("city", ((Airport)airport).getCity());
            element.addProperty("lon", ((Airport)airport).getLON());
            element.addProperty("lat", ((Airport)airport).getLAT());
            jArray.add(element);
        }
        markers.add("markers", jArray);
        resultJson = markers.toString();
        return resultJson;
    }
	
}
