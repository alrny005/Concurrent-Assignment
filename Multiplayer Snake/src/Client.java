/**
 * @author Liam Clark
 */
public class Client {
    private String clientID;
    private String password;

    Client(String ID, String pass) {
        clientID = ID;
        password = pass;
    }

    public String getID() {
        return clientID;
    }

    public String getPass(){
        return password;

    }
}
