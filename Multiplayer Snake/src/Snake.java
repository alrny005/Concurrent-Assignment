import java.util.Random;

/**
 * Class that represents one instance of a snake. Functions related to a snake from the Game class have been
 * imported into this class so that multiple snakes can exist at the same time.
 *
 * @author Norris Alrichani (alrny005)
 */

public class Snake {
    private int[][] snake = null;       // Represents a Snake object.
    private int direction = -1;         // Represents the direction the snake is travelling; 0 = UP, 1 = DOWN, 2 = LEFT, 3 = RIGHT.
    private int next_direction = -1;    // Represents the next direction the snake is travelling.

    // Get a certain position rather than the whole snake.
    public synchronized int getSnake(int i, int j) {
        return snake[i][j];
    }

    public synchronized void setSnake(int[][] intarray) {
        this.snake = intarray;
    }

    // Set a value at a certain point of snake.
    public synchronized void setSnake(int i, int j, int value) {
        this.snake[i][j] = value;
    }

    public synchronized int getDirection() {
        return direction;
    }

    public synchronized void setDirection(int direction) {
        this.direction = direction;
    }

    public synchronized int getNextDirection() {
        return next_direction;
    }

    public synchronized void setNextDirection(int next_direction) {
        this.next_direction = next_direction;
    }
}
