package eti.zako.restful.tools;

import eti.zako.persistance.PersistanceStore;

import java.util.HashMap;

public class JsonConstructor {

    public static String[] getAllDatabases(HashMap<String, PersistanceStore> persistances) {
        return new String[]{"baza"};
    }
}
