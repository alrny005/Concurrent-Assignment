public class Client {
    private String clientID;
    private String password;


    Client(String ID, String pass) {
        clientID = ID;
    }

    public String getID() {
        return clientID;
    }

    public boolean checkPass(String pass){
        return pass.equals(password);

    }
}
