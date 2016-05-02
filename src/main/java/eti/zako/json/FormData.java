package eti.zako.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}

