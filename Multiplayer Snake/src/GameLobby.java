import java.util.concurrent.ConcurrentHashMap;

public class GameLobby implements Runnable {
    private int user;
    ConcurrentHashMap<Integer,Client> lobby;
    /**
     * Constructor for the GameLobby Runnable, allows the thread to get a reference for the server and snake hashmap
     * as well as the index of the player
     * @param userIndex - finds the index for the user to be added, used as an ID in the concurrent hashmap
     */
    public GameLobby(int userIndex, ConcurrentHashMap<Integer,Client> clientLobby){
        user = userIndex;
        lobby = clientLobby;
    }


    int count = 0;
    public synchronized void Add(ConcurrentHashMap<Integer,Client> temp){
        while (count == 1){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lobby = temp;
        count += 1;
        this.notifyAll();

    }

    /**
     * when the thread is run, a user is logged in and a snake is being added to the concurrent hashmap
     */
    @Override
    public void run() {
        lobby.put(user,new Client("user"+user, "pass"+user));
    }

}
