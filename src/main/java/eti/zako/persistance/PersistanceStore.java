package eti.zako.persistance;

import eti.zako.persistance.model.*;
import org.garret.perst.Storage;

public class PersistanceStore {

    private String storageName;
    private Storage storage;

    public PersistanceStore() {
    }

    public PersistanceStore(Storage storage) {
        this.storage = storage;
    }

    public void setStoreName(String pStoreName) {
        storageName = pStoreName;
    }

    public void putAirport(Airport airport) {
        PersistanceRoot root = (PersistanceRoot) storage.getRoot();
        if (root == null) {
            // if root object was not specified, then storage is not yet
            // initialized
            // Perform initialization:
            // ... create root object
            root = new PersistanceRoot(storage);
            // ... register new root object
            storage.setRoot(root);
            // ... and import data from resource files
        }
        root.put(airport);
        storage.commit();
    }

    public void putCKIN(CKIN ckin) {
        PersistanceRoot root = (PersistanceRoot) storage.getRoot();
        if (root == null) {
            // if root object was not specified, then storage is not yet
            // initialized
            // Perform initialization:
            // ... create root object
            root = new PersistanceRoot(storage);
            // ... register new root object
            storage.setRoot(root);
            // ... and import data from resource files
        }
        root.put(ckin);
        storage.commit();
    }

    public void putFlight(Flight flight) {
        PersistanceRoot root = (PersistanceRoot) storage.getRoot();
        if (root == null) {
            // if root object was not specified, then storage is not yet
            // initialized
            // Perform initialization:
            // ... create root object
            root = new PersistanceRoot(storage);
            // ... register new root object
            storage.setRoot(root);
            // ... and import data from resource files
        }
        root.put(flight);
        storage.commit();
    }

    public void putGate(Gate gate) {
        PersistanceRoot root = (PersistanceRoot) storage.getRoot();
        if (root == null) {
            // if root object was not specified, then storage is not yet
            // initialized
            // Perform initialization:
            // ... create root object
            root = new PersistanceRoot(storage);
            // ... register new root object
            storage.setRoot(root);
            // ... and import data from resource files
        }
        root.put(gate);
        storage.commit();
    }

    public Storage getStorage() {
        return storage;
    }
}
