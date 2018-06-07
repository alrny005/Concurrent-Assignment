import java.util.concurrent.ConcurrentHashMap;

public class Client {
    private String clientID;
    private String password;



    Client(String ID, String pass) {
        clientID = ID;
    }

    public String getID() {
        return clientID;
    }

    public String getPass(){
        return password;

    }
}
