package eti.zako.persistance;

import eti.zako.persistance.model.*;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

import java.util.Date;

public class PersistanceManager {

    public static PersistanceStore createNewPersistance(String storeName) {
        PersistanceStore newStore = new PersistanceStore(createStorage(storeName));
        newStore.setStoreName(storeName);

        newStore.putAirport(new Airport("Lotnisko Gdansk", "Gdansk", "test", "test", 10, 100));
        newStore.putGate(new Gate(1, "10", "Warszawa", new Date(2016, 5, 15, 15, 30), new Date(2016, 5, 15, 16, 30), 0));
        newStore.putCKIN(new CKIN(1, "10", "Warszawa", new Date(2016, 5, 15, 15, 30), new Date(2016, 5, 15, 16, 30), 100, 0));
        newStore.putFlight(new Flight(1, "10", "Lotnisko Gdansk", "Warszawa", new Date(2016, 5, 15, 15, 30), 50, 150, 250));

        return newStore;
    }

    private static Storage createStorage(String pPath) {
        final Storage storage = StorageFactory.getInstance().createStorage();

        //storage.open(ApplicationRuntime.getAppDirectory() + "\\" + pPath + ".dbs");
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

        root.load();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                storage.commit();
                storage.close();
            }
        });

        return storage;
    }
}
