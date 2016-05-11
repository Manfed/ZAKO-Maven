package eti.zako.persistance.model;

import eti.zako.persistance.PersistanceStore;
import org.garret.perst.Persistent;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

public class Gate extends Persistent{

    public Integer ID;
    public String flightNumber;
    public String destination;
    public Date timeStart;
    public Date timeStop;
    public Integer delayCode;

    public Gate() {

    }

    public Gate(Integer ID, String flightNumber, String destination, Date timeStart, Date timeStop, Integer delayCode) {
        this.ID = ID;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.delayCode = delayCode;
    }

    @Override
    public void writeExternal(ObjectOutput out) {
        try {
            out.writeInt(ID);
            out.writeChars(flightNumber);
            out.writeChars(destination);
            out.writeObject(timeStart);
            out.writeObject(timeStop);
            out.writeInt(delayCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readExternal(ObjectInput in) {
        try {
            ID = in.readInt();
            flightNumber = in.readObject().toString();
            destination = in.readObject().toString();
            timeStart = (Date) in.readObject();
            timeStop = (Date) in.readObject();
            delayCode = in.readInt();
        } catch (IOException  e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Gate parse(PersistanceStore store, JSONObject object) {
        Gate newGate = new Gate();

        newGate.ID = Integer.parseInt(object.get("ID").toString());
        newGate.flightNumber = object.get("flightNumber").toString();
        newGate.destination = object.get("destination").toString();
        newGate.timeStart = (Date) object.get("timeStart");
        newGate.timeStop = (Date) object.get("timeStop");
        newGate.delayCode = Integer.parseInt(object.get("delayCode").toString());

        return newGate;
    }

    public static JSONObject toJSON(Gate gate) {
        JSONObject object = new JSONObject();
        object.put("ID", gate.ID);
        object.put("flightNumber", gate.flightNumber);
        object.put("destination", gate.destination);
        object.put("timeStart", gate.timeStart);
        object.put("timeStop", gate.timeStop);
        object.put("delayCode", gate.delayCode);

        return object;
    }
}
