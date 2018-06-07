import org.mapdb.*;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.*;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

public class MapDB {
    public ConcurrentNavigableMap<String, String> map() {

        DB db = DBMaker.newFileDB(new File("userCredentials"))
                .closeOnJvmShutdown()
                .encryptionEnable("password")
                .make();

        // open existing an collection (or create new)
        ConcurrentNavigableMap<String, String> userCredentials = db.getTreeMap("userCredentials");

        for (int i = 0; i < 100; i++) {
            userCredentials.put("user" + i, "pass" + i);
        }
        db.commit();
        //db.close();
        return userCredentials;
    }

}