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

    public int[][] getSnake() {
        return snake;
    }

    // Get a certain position rather than the whole snake.
    public int getSnake(int i, int j) {
        return snake[i][j];
    }

    public void setSnake(int[][] snake) {
        this.snake = snake;
    }

    // Set a value at a certain point of snake.
    public void setSnake(int i, int j, int value) {
        this.snake[i][j] = value;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getNextDirection() {
        return next_direction;
    }

    public void setNextDirection(int next_direction) {
        this.next_direction = next_direction;
    }
}
