package eti.zako.persistance.model;

import eti.zako.persistance.PersistanceStore;
import org.garret.perst.Persistent;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

public class CKIN extends Persistent{

    public Integer ID;
    public String flightNumber;
    public String destination;
    public Date timeStart;
    public Date timeStop;
    public Integer luggageLimit;
    public Integer delayCode;

    public CKIN() {

    }

    public CKIN(Integer ID, String flightNumber, String destination, Date timeStart, Date timeStop, Integer luggageLimit, Integer delayCode) {
        this.ID = ID;
        this. flightNumber = flightNumber;
        this.destination = destination;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.luggageLimit = luggageLimit;
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
            out.writeInt(luggageLimit);
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
            luggageLimit = in.readInt();
            delayCode = in.readInt();
        } catch (IOException  e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static CKIN parse(PersistanceStore store, JSONObject object) {
        CKIN newCKIN = new CKIN();

        newCKIN.ID = Integer.parseInt(object.get("ID").toString());
        newCKIN.flightNumber = object.get("flightNumber").toString();
        newCKIN.destination = object.get("destination").toString();
        newCKIN.timeStart = (Date) object.get("timeStart");
        newCKIN.timeStop = (Date) object.get("timeStop");
        newCKIN.luggageLimit = Integer.parseInt(object.get("luggageLimit").toString());
        newCKIN.delayCode = Integer.parseInt(object.get("delayCode").toString());

        return newCKIN;
    }

    public static JSONObject toJSON(CKIN ckin) {
        JSONObject object = new JSONObject();
        object.put("ID", ckin.ID);
        object.put("flightNumber", ckin.flightNumber);
        object.put("destination", ckin.destination);
        object.put("timeStart", ckin.timeStart);
        object.put("timeStop", ckin.timeStop);
        object.put("luggageLimit", ckin.luggageLimit);
        object.put("delayCode", ckin.delayCode);

        return object;
    }
}
