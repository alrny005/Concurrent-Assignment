/**
 * the Client class stores client information in an object
 * @author Liam Clark
 */
class Client {
    private String clientID;
    private String password;

    Client(String ID, String pass) {
        clientID = ID;
        password = pass;
    }

    String getID() {
        return clientID;
    }

    String getPass(){
        return password;

    }
}
