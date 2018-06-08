import java.util.concurrent.ConcurrentHashMap;

/**
 * A GameGrid class that represents the grid on which the game of Snake will be played on.
 *
 *@author Norris Alrichani (alrny005), Liam Clark
 *
 * Norris Alrichani - Refactored the original code to allow the grid to work in a separate class.
 * Liam Clark - converted the integer array grid into a concurrent hashmap that uses the width variable to simulate
 *              a 2-Dimensional map.
 *
 */

class GameGrid {
        private ConcurrentHashMap<Integer,Integer> grid;    // An object that represents the grid that the game will run on.
    private int width;                                  // The width of the grid, which can be set to a desired size.

    // Syncrhonized as we definitely want only one instance setting the grid at any given time.
    synchronized void setGrid(ConcurrentHashMap<Integer,Integer> grid, int gridWidth) {
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
     * @param i The horizontal value of the location being retrieved.
     * @param j The vertical value of the location being retrieved.
     * @return the value of the hashmap with the key that matches the location (i,j) of the game state
     */
    int getStatus(int i, int j) {
        return grid.get((j*width)+i);
    }

    /**
     * Set the status (as defined in the Game class) at a given location in the grid.
     * @param i The horizontal value of the location being retrieved.
     * @param j The vertical value of the location being retrieved.
     * @param status The value stored at the x, y location.
     */
    void setStatus(int i, int j, int status) {
        grid.put(((j*width)+i),status);
    }
}
