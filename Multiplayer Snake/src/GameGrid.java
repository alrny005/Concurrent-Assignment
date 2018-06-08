import java.util.concurrent.ConcurrentHashMap;

/**
 * A GameGrid class that represents the grid on which the game of Snake will be played on.
 * This class has synchronized methods so that it is able to safely communicate (thread-wise) with the Snake threads.
 *
 *@author Norris Alrichani, Liam Clark
 *
 * Liam Clark - converted the integer array grid into a concurrent hashmap that uses the width variable to simulate
 * a 2 Dimensional map
 *
 */

public class GameGrid {
    private int width;
    private ConcurrentHashMap<Integer,Integer> grid;


    public synchronized void setGrid(ConcurrentHashMap<Integer,Integer> grid, int gridWidth) {
        this.grid = grid;
        width = gridWidth;
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
        return grid.get((j*width)+i);
    }

    /**
     * Set the status (as defined in the Game class) at a given location in the grid.
     * @param i
     * @param j
     * @param status
     */
    public void setStatus(int i, int j, int status) {
        grid.put(((j*width)+i),status);
    }
}
