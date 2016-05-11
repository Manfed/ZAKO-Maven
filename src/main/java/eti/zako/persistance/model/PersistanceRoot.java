package eti.zako.persistance.model;

import org.garret.perst.FieldIndex;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;

public class PersistanceRoot extends Persistent {

    public static enum INSTANCE_TYPE {
        AIRPORT,
        CKIN,
        FLIGHT,
        GATE
    }

    public FieldIndex indexFlight;
    public FieldIndex indexCKIN;
    public FieldIndex indexGate;
    public FieldIndex indexAirport;

    public PersistanceRoot() {

    }

    public PersistanceRoot(Storage db) {
        super(db);
        indexFlight = db.createFieldIndex(Flight.class, "ID", true);
        indexCKIN = db.createFieldIndex(CKIN.class, "ID", true);
        indexGate = db.createFieldIndex(Gate.class, "ID", true);
        indexAirport = db.createFieldIndex(Airport.class, "airportID", true);
    }

    public void put(Persistent obj) {
        if(obj instanceof Flight) {
            indexFlight.put(obj);
        } else if(obj instanceof CKIN) {
            indexCKIN.put(obj);
        } else if(obj instanceof Gate) {
            indexGate.put(obj);
        } else if(obj instanceof Airport) {
            indexAirport.put(obj);
        }
    }
}
