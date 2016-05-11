package eti.zako.restful;

import eti.zako.persistance.PersistanceManager;
import eti.zako.persistance.PersistanceStore;
import eti.zako.persistance.model.*;
import eti.zako.restful.tools.JsonConstructor;
import eti.zako.restful.tools.JsonResponder;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;

@Path("/{database}")
@RequestScoped
public class RestResource {

    @Context
    private UriInfo context;

    private final HashMap<String, PersistanceStore> persistances;

    public RestResource(UriInfo context) {
        this.context = context;
        this.persistances = new HashMap<String, PersistanceStore>();
    }

    public RestResource() {
        this.persistances = new HashMap<String, PersistanceStore>();
    }

    @GET
    @Produces("application/json")
    public String otherOperations(@PathParam("database") String operation) {
        if (operation.equalsIgnoreCase("listdatabases")) {
            String[] databases = JsonConstructor.getAllDatabases(persistances);

            return JsonResponder.getListDatabases(databases);
        } else if (operation.equalsIgnoreCase("ping")) {
            return "{\"response\":\"pong\"}";
        }

        return "{\"response\":\"" + operation + "\"}";
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String otherPOSTOperations(@PathParam("database") String operation, String jsonName) {
        if (operation.equalsIgnoreCase("db_create")) {
            JSONObject json = (JSONObject) JSONValue.parse(jsonName);
            String dbName = json.get("pStoreName").toString();
            if (persistances.containsKey(dbName)) {
                return "{\"response\":\"already_exists\"\"}";
            }
            persistances.put(dbName, PersistanceManager.createNewPersistance(dbName));

            return "{\"" + operation + "\":\"OK\",\"response\":\"" + dbName + "\"}";
        }
        return "{\"response\":\"" + operation + "\"}";
    }

    @GET
    @Produces("application/json")
    @Path("/ping")
    public String ping2(@PathParam("database") String database) {
        return "{\"response\":\"" + database + "\"}";
    }

    @GET
    @Produces("application/json")
    @Path("/getAll")
    public Response getAll(@PathParam("database") String database) {
        if (!persistances.containsKey(database)) {
            persistances.put(database, PersistanceManager.createNewPersistance(database));
        }
        PersistanceStore store = persistances.get(database);

        return Response.ok().entity(JsonResponder.getAllData(store)).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
    }

    //    @GET
//    @Produces("application/json")
//    @Path("/giveProbes")
//    public String getProbes(@PathParam("database") String database) {
//        ArrayList<String> probes = databaseOperations.getAllProbes(database + ".db");
//        JSONObject allDatesJson = new JSONObject();
//        JSONArray allDatesArray = new JSONArray();
//
//        for (String s : probes) {
//            allDatesArray.add(s);
//        }
//        allDatesJson.put("allDates", allDatesArray);
//
//        return allDatesJson.toJSONString();
//    }
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/put")
    public String put(@PathParam("database") String database, String object) {
        if (!persistances.containsKey(database)) {
            persistances.put(database, PersistanceManager.createNewPersistance(database));
        }
        PersistanceStore store = persistances.get(database);
        if (store == null) {
            return "{\"response\":\"Not ok\"}";
        }
        JSONObject offer = (JSONObject) JSONValue.parse(object);

        String type = (String) offer.get("objType");
        JSONObject jsonObject = (JSONObject) offer.get("obj");
        JSONObject requestResponse = new JSONObject();

        if (type.equals(PersistanceRoot.INSTANCE_TYPE.AIRPORT.toString())) {
            Airport tmpAirport = Airport.parse(store, jsonObject);

            store.putAirport(tmpAirport);
        } else if (type.equals(PersistanceRoot.INSTANCE_TYPE.CKIN.toString())) {
            CKIN tmpCKIN = CKIN.parse(store, jsonObject);

            store.putCKIN(tmpCKIN);
        } else if (type.equals(PersistanceRoot.INSTANCE_TYPE.FLIGHT.toString())) {
            Flight tmpFlight = Flight.parse(store, jsonObject);

            store.putFlight(tmpFlight);
        } else if (type.equals(PersistanceRoot.INSTANCE_TYPE.GATE.toString())) {
            Gate tmpGate = Gate.parse(store, jsonObject);

            store.putGate(tmpGate);
        }

        return "{\"response\":\"" + database + "\"}";//requestResponse.toJSONString();
    }
}
