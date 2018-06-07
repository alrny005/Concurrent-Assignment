import java.util.concurrent.ConcurrentHashMap;

public class Client {
    private String clientID;
    private String password;


    ConcurrentHashMap<String, String> clients;

    Client(String ID, String pass) {
        clientID = ID;
        clients.put(clientID,password);
    }

    public String getID() {
        return clientID;
    }

    public String getPass(){
        return password;

    }
}