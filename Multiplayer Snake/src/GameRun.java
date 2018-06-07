import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class GameRun implements  Runnable {
    int playerCount;
    int offset;
    ConcurrentHashMap<Integer, Snake> snakeMap;
    Random rand;
    Game thisGame;

    public GameRun(int threadNumber, int players, ConcurrentHashMap<Integer, Snake> map, Game _game) {
        playerCount = players;
        offset = threadNumber;
        snakeMap = map;
        rand = new Random();
        thisGame = _game;
    }

    public void run() {
        for (int i = 4+offset; i < playerCount; i +=3) {
//            if (playerCount > 4) {
//                Random rand = new Random();
//            }
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
                    //setDirectionSnake(snakeMap.get(i));
                    snakeMap.get(i).setDirection(snakeMap.get(i).getNextDirection());
                    thisGame.moveSnake(snakeMap.get(i), i);
                }
            }
        }

        // Move every snake according to the direction it has chosen.
        for (int i = offset; i <playerCount; i+=3)
        {
            if (snakeMap.get(i) != null) {
                snakeMap.get(i).setDirection(snakeMap.get(i).getNextDirection());
                thisGame.moveSnake(snakeMap.get(i), i);
            }
        }
    }
}
