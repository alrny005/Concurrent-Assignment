import org.mapdb.*;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class MapDB {
    public void map(int UsersToCreate) {

        DB db = DBMaker.newFileDB(new File("userCredentials"))
                .closeOnJvmShutdown()
                .encryptionEnable("password")
                .make();

        // open existing an collection (or create new)
        ConcurrentNavigableMap<String, String> userCredentials = db.getTreeMap("userCredentials");
        //userCredentials.put(username password)

//put 100

        db.commit();

        db.close();
    }

}