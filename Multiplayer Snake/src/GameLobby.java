import java.util.concurrent.ConcurrentHashMap;

public class GameLobby implements Runnable {
    private int user;
    private ConcurrentHashMap<Integer,Client> lobby;
    private int count = 0;

    /**
     * Constructor for the GameLobby Runnable, allows the thread to get a reference for the server and snake hashmap
     * as well as the index of the player
     * @param userIndex - finds the index for the user to be added, used as an ID in the concurrent hashmap
     */
    GameLobby(int userIndex, ConcurrentHashMap<Integer,Client> clientLobby){
        user = userIndex;
        lobby = clientLobby;
    }

    private synchronized void Add(){
        while (count == 1){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lobby.put(user,new Client("user"+user, "pass"+user));
        count += 1;
        this.notifyAll();
    }

    /**
     * when the thread is run, a user is logged in and a snake is being added to the concurrent hashmap
     */
    @Override
    public void run() {
        Add();
    }

}
