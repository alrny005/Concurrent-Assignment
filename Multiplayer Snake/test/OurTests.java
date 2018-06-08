import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

public class OurTests {

    /**
     * this test checks the validation of the GameServer class, if the test passes that means a snake is not created
     * when the validation check fails
     */
    @Test
    public void liamsTest(){
        GameServer testServer = new GameServer();
        ConcurrentHashMap<Integer, Snake> snakeMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, Client> clientMap = new ConcurrentHashMap<>();
        clientMap.put(0,new Client("fakeID","fakePass"));
        testServer.validate(clientMap,snakeMap,0);
        Assert.assertTrue(snakeMap.size() == 0);
    }

    /**
     * this test checks that a new client is created with a set ID.
     */
    @Test
    public void timonsTest(){
        String pw = "testPass";
        String  ID = "testID";
        Client testClient = new Client(ID, pw);
        Assert.assertTrue(ID.equals(testClient.getID()));
    }

}
