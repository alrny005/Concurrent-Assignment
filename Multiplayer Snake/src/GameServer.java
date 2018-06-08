import java.util.concurrent.*;

class GameServer {
    private  MapDB mapDB;
    /**
     * Constructor which creates a concurrent hashmap object
     * @author Timon Groza - validated clients based on mapDB, ensuring they existed before creating a snake
     */
    GameServer() {

        mapDB = new MapDB();
    }

    void validate(ConcurrentHashMap<Integer, Client> buffer, ConcurrentHashMap<Integer,Snake> snakeMap, int index) {
        Client temp = buffer.get(index);
        //check that password is correct for the user, then add them into the snakeMap
        if(mapDB.map().get(temp.getID()).equals(temp.getPass())){
            snakeMap.put(index,new Snake());
        }
    }
}
