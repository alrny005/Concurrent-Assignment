import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.concurrent.*;

public class GameServer {//} implements Runnable{

    private String clientUsername;
    private String clientPassword;
    private MapDB clientMap = new MapDB();


    ConcurrentHashMap<String, String> userCredentials = new ConcurrentHashMap<String, String>();

    public GameServer() {
        userCredentials = new ConcurrentHashMap<>();

    }
    public void logInUser(String username, String password){
        userCredentials.put(username,password);
    }
}