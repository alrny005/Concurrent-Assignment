import java.util.concurrent.ConcurrentHashMap;

/**
 * A GameGrid class that represents the grid on which the game of Snake will be played on.
 * This class has synchronized methods so that it is able to safely communicate (thread-wise) with the Snake threads.
 *
 *@author Norris Alrichani, Liam Clark
 *
 */

public class GameGrid {
    private ConcurrentHashMap<Integer,Integer> grid;

    /**
     * Make this grid int array equal to the argument int array.
     * @param grid
     */
    public synchronized void setGrid(ConcurrentHashMap<Integer,Integer> grid) {
        this.grid = grid;
    }

    /**
     * Retrieve the value(status) at location [i][j] on the grid.
     * The value of status has different meanings:
     *      EMPTY = 0;
     *      FOOD_BONUS = 1;
     *      FOOD_MALUS = 2;
     *      BIG_FOOD_BONUS = 3;
     *      SNAKE = 4;
     * @param i
     * @param j
     * @return grid[i][j]
     */
    public int getStatus(int i, int j) {
        return grid.get((j*1000)+i);
    }

    /**
     * Set the status (as defined in the Game class) at a given location in the grid.
     * @param i
     * @param j
     * @param status
     */
    public void setStatus(int i, int j, int status) {
        grid.put(((j*1000)+i),status);
//        grid[i][j] = status;
    }
}
