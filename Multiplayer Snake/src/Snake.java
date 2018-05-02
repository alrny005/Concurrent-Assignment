/**
 * Class that represents one instance of a snake. Functions related to a snake from the Game class have been
 * imported into this class so that multiple snakes can exist at the same time.
 *
 * @author Norris Alrichani (alrny005)
 */


public class Snake {
    private int[][] snake = null;

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

}
