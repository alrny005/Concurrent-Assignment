import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameRun implements  Runnable {
    private int playerCount;
    private int offset;
    private ConcurrentHashMap<Integer, Snake> snakeMap;
    private Random rand;
    private Game thisGame;
    private long cycleTime=0;
    private long sleepTime =0;
    private long speed = 70;

    GameRun(int threadNumber, int players, ConcurrentHashMap<Integer, Snake> map, Game _game) {
        playerCount = players;
        offset = threadNumber;
        snakeMap = map;
        rand = new Random();
        thisGame = _game;
    }

    public void run() {
        while (true) {
            for (int i = 4 + offset; i < playerCount; i += 3) {
                if (snakeMap.get(i) != null) {
                    int nextDirection = rand.nextInt(4);
                    int directionChecker = snakeMap.get(i).getDirection();
                    if (directionChecker == 0 && nextDirection == 1) {
                        nextDirection++;
                    } else if (directionChecker == 1 && nextDirection == 0) {
                        nextDirection--;
                    } else if (directionChecker == 2 && nextDirection == 3) {
                        nextDirection++;
                    } else if (directionChecker == 3 && nextDirection == 2) {
                        nextDirection--;
                    } else {
                        snakeMap.get(i).setNextDirection(nextDirection);
                        snakeMap.get(i).setDirection(snakeMap.get(i).getNextDirection());
                        thisGame.moveSnake(snakeMap.get(i), i);
                    }
                }
            }

            // Move every snake according to the direction it has chosen.
            for (int i = offset; i < playerCount; i += 3) {
                if (snakeMap.get(i) != null) {
                    snakeMap.get(i).setDirection(snakeMap.get(i).getNextDirection());
                    thisGame.moveSnake(snakeMap.get(i), i);
                }
            }
            cycleTime = System.currentTimeMillis();
            cycleTime = System.currentTimeMillis() - cycleTime;
            sleepTime = speed - cycleTime;
            if (sleepTime < 0)
                sleepTime = 0;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
