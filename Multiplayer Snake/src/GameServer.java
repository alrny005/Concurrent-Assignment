import java.util.concurrent.*;

/**
 * The GameServer class pulls clients from the buffer and compares them to the entries in the mapDB database to
 * validate them and adds snakes to the snake hashmap if the clients are valid
 *
 * @author Timon Groza - validated clients based on mapDB, ensuring they existed before creating a snake
 */
class GameServer {
    private  MapDB mapDB;
    /**
     * Constructor which creates a concurrent hashmap object
     */
    GameServer() {

        mapDB = new MapDB();
    }

    void validate(ConcurrentHashMap<Integer, Client> buffer, ConcurrentHashMap<Integer,Snake> snakeMap, int index) {
        Client temp = buffer.get(index);
        //check that password is correct for the user, then add them into the snakeMap
        if(mapDB.map().get(temp.getID()) != null) {
            if (mapDB.map().get(temp.getID()).equals(temp.getPass())) {
                snakeMap.put(index, new Snake());
            }
        }
    }
}
