package eti.zako.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.controller.HibernateController;

import com.google.gson.JsonArray;

import eti.zako.sqlite.model.Airport;
import eti.zako.sqlite.model.FlightPath;

public class FormData {
    
    private Form form;
    
    public Form getForm() {
        return this.form;
    }
    
    public class Form {
        private String from;
        private String to;
        private String date;
        private String time;
        
        public String getFrom() {
            return this.from;
        }
        
        public String getTo() {
            return this.to;
        }
        
        public Date getDate() {
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            Date formDate = null;
            try {
                formDate = format.parse(this.date + " " + this.time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formDate;
        }
    }
    
    /**
     * Funkcja zwracająca Json'a z trasą
     */
    public String parseForm() {
        String resultJson = null;
        List<JsonArray> jsonPaths = new ArrayList<JsonArray>();
        
        Airport from = HibernateController.<Airport>getSingleElement("Airport", "city='" + getForm().getFrom() + "'");
        Airport to = HibernateController.<Airport>getSingleElement("Airport", "city='" + getForm().getTo() + "'");
        
        List<FlightPath> paths = HibernateController.getFlightPath(from.getAirportId(), to.getAirportId());
        for(FlightPath fPath : paths) {
            jsonPaths.add(JSONBuilder.preparePathJson(fPath, getForm().getDate()));
        }
        
        resultJson = JSONBuilder.mergePaths(jsonPaths);
        return resultJson;
    }
}

