import java.util.concurrent.*;

public class GameServer {
    ConcurrentHashMap<String, String> userCredentials;

    /**
     * Constructor which creates a concurrent hashmap object
     */
    public GameServer() {
        userCredentials = new ConcurrentHashMap<>();
    }

    /**
     * adds user information to the userCredentials Hashmap
     * @param username - name of the user
     * @param password - password for the user to login
     */
    public void logInUser(String username, String password){
        userCredentials.put(username,password);
    }
}
