/**
 * A GameGrid class that represents the grid on which the game of Snake will be played on.
 * This class has synchronized methods so that it is able to safely communicate (thread-wise) with the Snake threads.
 */

public class GameGrid {
    private int[][] grid = null;

    /**
     * @summary Retrieve the grid int array.
     * @return grid[][]
     */
    public synchronized int[][] getGrid() {
        return grid;
    }

    /**
     * @summary Make this grid int array equal to the argument int array.
     * @param grid
     */
    public synchronized void setGrid(int[][] grid) {
        this.grid = grid;
    }

    /**
     * @summary Retrieve the value at location [i][j] on the grid.
     * @param i
     * @param j
     * @return grid[i][j]
     */
    public synchronized int getGrid(int i, int j) {
        return grid[i][j];
    }

    /**
     * @summary Set the status (as defined in the Game class) at a given location in the grid.
     * @param i
     * @param j
     * @param status
     */
    public synchronized void setStatus(int i, int j, int status) {
        // The value of status has different meanings:
        //      EMPTY = 0;
        //      FOOD_BONUS = 1;
        //      FOOD_MALUS = 2;
        //      BIG_FOOD_BONUS = 3;
        //      SNAKE = 4;
        //
        grid[i][j] = status;
    }
}