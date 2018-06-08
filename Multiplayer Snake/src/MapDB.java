import org.mapdb.*;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.*;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * MapDB is a database of user credentials
 * @author Timon Groza - Created the database and simulated 100 users to be checked against
 */
class MapDB {
    ConcurrentNavigableMap<String, String> map() {

        DB db = DBMaker.newFileDB(new File("userCredentials"))
                .closeOnJvmShutdown()
                .encryptionEnable("password")
                .make();

        // open existing an collection (or create new)
        ConcurrentNavigableMap<String, String> userCredentials = db.getTreeMap("userCredentials");

        //simulates a registry of 100 players to be checked against when players try to login
        for (int i = 0; i < 100; i++) {
            userCredentials.put("user" + i, "pass" + i);
        }
        db.commit();
        return userCredentials;
    }

}