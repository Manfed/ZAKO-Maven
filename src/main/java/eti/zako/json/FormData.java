package eti.zako.json;

public class FormData {
    
    private Form form;
    
    class Form {
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
        
        public String getDate() {
            return this.date;
        }
        
        public String getTime() {
            return this.time;
        }
    }
    
    public Form getForm() {
        return this.form;
    }
}

