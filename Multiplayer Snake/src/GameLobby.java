import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class GameLobby implements Callable { // implements WindowListener,KeyListener {
    GameServer server;
    private int userCount;
    ConcurrentHashMap<Integer,Snake> snakeMap = new ConcurrentHashMap<Integer, Snake>();
    public GameLobby(GameServer _server, int users){
        server = _server;
        userCount = users;
    }

    @Override
    public ConcurrentHashMap call() {
        for (int i=0; i<userCount;i++){
            server.logInUser("user"+i,"pass"+i);
            snakeMap.put(i,new Snake());
        }
        return snakeMap;
    }
}
