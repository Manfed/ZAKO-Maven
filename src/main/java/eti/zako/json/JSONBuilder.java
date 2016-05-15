package eti.zako.json;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
	
}
