import java.util.concurrent.*;

public class GameServer {
    private  MapDB mapDB;
    /**
     * Constructor which creates a concurrent hashmap object
     */
    public GameServer() {

        mapDB = new MapDB();
    }

    public void validate(ConcurrentHashMap<Integer, Client> buffer, ConcurrentHashMap<Integer,Snake> snakeMap, int index) {
        Client temp = buffer.get(index);
        if(mapDB.map().get(temp.getID()).equals(temp.getPass())){
            System.out.println("test");
            snakeMap.put(index,new Snake());
        }
    }
}
