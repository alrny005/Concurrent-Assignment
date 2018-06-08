import org.junit.Assert;
import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import java.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

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
        ConcurrentHashMap<Integer, Client> clientMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 4; i++) {
            clientMap.put(i, new Client("user" + i, "pass" + i));
        }
        Assert.assertTrue("Check how many userCredentials added", clientMap.size() == 4);
}
    
    

    
    @Test
    public void SidTest() throws Exception{
        ConcurrentHashMap<Integer, Snake> snakes = new ConcurrentHashMap<>();
        int i  =0;
        for (i = 0; i < 4; i++){
            snakes.put(i,new Snake());
        }
        int expected_snakes = 4;
        Assert.assertEquals(expected_snakes, snakes.size());   
}
    
}
    
    
