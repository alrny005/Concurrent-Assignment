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

    /**
     * This test will check if a snake is correctly deleted when it collides with another snake.
     * The point of this test is to show that ConcurrentHashMap will correctly remove snakes when the game is running.
     */
    @Test
    public void norrisTest() {
        ConcurrentHashMap<Integer, Snake> snakeMap = new ConcurrentHashMap<>();

        // Create two snakes and put them into the hashmap.
        Snake snake1 = new Snake();
        Snake snake2 = new Snake();

        snakeMap.put(0, snake1);
        snakeMap.put(1, snake2);

        // Remove the snake from the hashmap and assert.
        snakeMap.remove(1);
        Assert.assertTrue(snakeMap.get(1) == null);
    }
    
    @Test
    public void DamberTest() throws Exception{

        DB db = DBMaker.newFileDB(new File("userCredentials"))
                .closeOnJvmShutdown()
                .encryptionEnable("password")
                .make();
        ConcurrentNavigableMap<String, String> userCredentials = db.getTreeMap("userCredentials");

        for (int i = 0; i < 4; i++) {
            userCredentials.put("user" + i, "pass" + i);
        }
        Assert.assertTrue("Check how many userCredentials added", userCredentials.size() == 4);
}
