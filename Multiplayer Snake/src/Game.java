import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peuch
 */

public class Game implements KeyListener, WindowListener {
    // PLAYER COUNT
    //  (Select from 1, 4 or 100 players; the latter has 4 real players and 96 AI).
    private final static int PLAYERS = 5;

    // KEYS MAP
    public final static int UP = 0;
    public final static int DOWN = 1;
    public final static int LEFT = 2;
    public final static int RIGHT = 3;

    // GRID CONTENT
    public final static int EMPTY = 0;
    public final static int FOOD_BONUS = 1;
    public final static int FOOD_MALUS = 2;
    public final static int BIG_FOOD_BONUS = 3;
    public final static int SNAKE = 4;
    GameGrid grid = new GameGrid();
    static ConcurrentHashMap<Integer,Snake> snakeMap;
    private int height = 1000;
    private int width = 1000;
    private int gameSize = 100;
    private long speed = 70;
    private Frame frame = null;
    private Canvas canvas = null;
    private Graphics graph = null;
    private BufferStrategy strategy = null;
    private boolean game_over = false;
    private boolean paused = false;

    private int score = 0;
    private int grow = 0;

    private int seconde, minute, milliseconde = 0; // Clock values
    private long cycleTime = 0;
    private long sleepTime = 0;
    private int bonusTime = 0;
    private int malusTime = 0;

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        GameServer server = new GameServer();
        //Thread t1 = new Thread(new GameLobby(server,40));
        //t1.run();
        //GameLobby gl = new GameLobby(server,40);
        GameLobby logins = new GameLobby(server, PLAYERS); //TODO changed number of users so loop in initGame works (see TODO there).
        snakeMap = logins.call();
        start();
    }

    public static void start() {
        Game game = new Game();
        game.init();
        game.mainLoop();
    }

    public Game() {
        super();
        frame = new Frame();
        canvas = new Canvas();
        int[][] newGrid = new int[gameSize][gameSize];
        grid.setGrid(newGrid);
    }

    public void init() {
        frame.setSize(width + 7, height + 27);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        canvas.setSize(width + 7, height + 27);
        frame.add(canvas);
        canvas.addKeyListener(this);
        frame.addWindowListener(this);
        frame.dispose();
        frame.validate();
        frame.setTitle("Snake");
        frame.setVisible(true);
        canvas.setIgnoreRepaint(true);
        canvas.setBackground(Color.WHITE);

        canvas.createBufferStrategy(2);

        strategy = canvas.getBufferStrategy();
        graph = strategy.getDrawGraphics();

        setSnakes();
        initGame();
        renderGame();
    }

    public void mainLoop() {
        while (!game_over) {
            cycleTime = System.currentTimeMillis();
            if (!paused) {
                // Move every snake according to the direction it has chosen.
                for (int i = 0; i < snakeMap.size(); i++){
                    setDirectionSnake(snakeMap.get(i));
                    moveSnake(snakeMap.get(i));
                }
            }
            renderGame();
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

    /**
     * @summary Initialise the value in the snake hashmap based on the number of players.
     */
    public void setSnakes() {
        for (int i = 0; i < snakeMap.size(); i++) {
            //Snake tempSnake = new Snake();
            //tempSnake.setSnake(new int[gameSize * gameSize][2]);
            //snakeMap.put(i, tempSnake);
            snakeMap.get(i).setSnake(new int[gameSize * gameSize][2]);
        }
    }

    private void initGame() {
        // Initialise tabs
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                grid.setStatus(i, j, EMPTY);
            }
        }

        // Initialise every snake in the hashmap.
        for (int i = 0; i < gameSize * gameSize; i++) {
            for (int index = 0; index < snakeMap.size(); index++) {
                snakeMap.get(index).setSnake(i, 0, -1);
                snakeMap.get(index).setSnake(i, 1, -1);
            }
        }

        // Initialise every snake onto the grid.
        for (int i = 0; i < snakeMap.size(); i++) {
            // TODO still need to set a value that can be unique to 100 snakes within the game board. Value must be a
            //  factor of the gameSize (currently 70; so any number divisible by 7 works) or else the snake will be
            //  invisible in the corner until it moves.
            int value = gameSize / 10 + (i * 10); // Value that will help determine the snake's location on the grid.
            snakeMap.get(i).setSnake(0, 0, value);
            snakeMap.get(i).setSnake(0, 1, value);
            grid.setStatus(value, value, SNAKE);
        }

        placeBonus(FOOD_BONUS);
    }

    private void renderGame() {
        int gridUnit = height / gameSize;
        canvas.paint(graph);
        do {
            do {
                graph = strategy.getDrawGraphics();
                // Draw Background
                graph.setColor(Color.WHITE);
                graph.fillRect(0, 0, width, height);
                // Draw snake, bonus ...
                int gridCase = EMPTY;
                for (int i = 0; i < gameSize; i++) {
                    for (int j = 0; j < gameSize; j++) {
                        gridCase = grid.getStatus(i, j);
                        switch (gridCase) {
                            case SNAKE:
                                // If the status at a given location is not EMPTY, then it can be assumed that the
                                //  snake exists in that square.

//                                for (int index = 0; index < snakeMap.size(); index++) {
//                                    // TODO Example of how we need to use snakeMap to draw the snakes.
//                                    // System.out.println(snakeMap.get(index));
//                                    if (snakeMap.get(index).getSnake() == new int[i][j]) {
//                                        // Set the colour for Player 1.
//                                        if (index == 0) {
//                                            graph.setColor(Color.BLUE);
//                                        }
//                                        // Set the colour for Player 2.
//                                        else if (index == 1) {
//                                            graph.setColor(Color.RED);
//                                        }
//                                        // Set the colour for Player 3.
//                                        else if (index == 2) {
//                                            graph.setColor(Color.GREEN);
//                                        }
//                                        // Set the colour for Player 4.
//                                        else if (index == 3) {
//                                            graph.setColor(Color.YELLOW);
//                                        }
//                                        // Set the colour for all other AI snakes.
//                                        else {
//                                            graph.setColor(Color.PINK);
//                                        }
//
//                                        graph.fillOval(i * gridUnit, j * gridUnit, gridUnit, gridUnit);
//                                    }
//                                }

                                //graph.fillOval(i * gridUnit, j * gridUnit, gridUnit, gridUnit);

                                graph.setColor(Color.BLACK);
                                graph.fillOval(i * gridUnit, j * gridUnit, gridUnit, gridUnit);
                                break;

                            case FOOD_BONUS:
                                graph.setColor(Color.darkGray);
                                graph.fillOval(i * gridUnit + gridUnit / 4, j * gridUnit + gridUnit / 4, gridUnit / 2,
                                        gridUnit / 2);
                                break;
                            case FOOD_MALUS:
                                graph.setColor(Color.RED);
                                graph.fillOval(i * gridUnit + gridUnit / 4, j * gridUnit + gridUnit / 4, gridUnit / 2,
                                        gridUnit / 2);
                                break;
                            case BIG_FOOD_BONUS:
                                graph.setColor(Color.GREEN);
                                graph.fillOval(i * gridUnit + gridUnit / 4, j * gridUnit + gridUnit / 4, gridUnit / 2,
                                        gridUnit / 2);
                                break;
                            default:
                                break;
                        }
                    }
                }
                graph.setFont(new Font(Font.SANS_SERIF, Font.BOLD, height / 40));
                if (game_over) {
                    graph.setColor(Color.RED);
                    graph.drawString("GAME OVER", height / 2 - 30, height / 2);
                    graph.drawString("YOUR SCORE : " + score, height / 2 - 40, height / 2 + 50);
                    //graph.drawString("YOUR TIME : " + getTime(), height / 2 - 42, height / 2 + 100);
                } else if (paused) {
                    graph.setColor(Color.RED);
                    graph.drawString("PAUSED", height / 2 - 30, height / 2);
                }
                graph.setColor(Color.BLACK);
                graph.drawString("SCORE = " + score, 10, 20);
                //graph.drawString("TIME = " + getTime(), 100, 20); // Clock
                graph.dispose();
            } while (strategy.contentsRestored());
            // Draw image from buffer
            strategy.show();
            Toolkit.getDefaultToolkit().sync();
        } while (strategy.contentsLost());
    }

    // TODO Implement time feature(?)
//    private String getTime() {
//        String temps = new String(minute + ":" + seconde);
//        if (direction < 0 || paused)
//            return temps;
//
//        milliseconde++;
//        if (milliseconde == 14) {
//            seconde++;
//            milliseconde = 0;
//        }
//        if (seconde == 60) {
//            seconde = 0;
//            minute++;
//        }
//
//        return temps;
//    }

    /**
     * @summary Set the direction that the snake is about to travel in.
     * @param directionSnake
     */
    private synchronized void setDirectionSnake(Snake directionSnake) {
        directionSnake.setDirection(directionSnake.getNextDirection());
    }

    /**
     * @summary Move the snake in the direction that is chosen.
     * @param movedSnake
     */
    private synchronized void moveSnake(Snake movedSnake) {
        if (movedSnake.getDirection() < 0) {
            return;
        }
        int ymove = 0;
        int xmove = 0;

        switch (movedSnake.getDirection()) {
            case UP:
                xmove = 0;
                ymove = -1;
                break;
            case DOWN:
                xmove = 0;
                ymove = 1;
                break;
            case RIGHT:
                xmove = 1;
                ymove = 0;
                break;
            case LEFT:
                xmove = -1;
                ymove = 0;
                break;
            default:
                xmove = 0;
                ymove = 0;
                break;
        }

        int tempx = movedSnake.getSnake(0, 0);
        int tempy = movedSnake.getSnake(0, 1);

        int fut_x = movedSnake.getSnake(0, 0) + xmove;
        int fut_y = movedSnake.getSnake(0, 1) + ymove;

        if (fut_x < 0)
            fut_x = gameSize - 1;
        if (fut_y < 0)
            fut_y = gameSize - 1;
        if (fut_x >= gameSize)
            fut_x = 0;
        if (fut_y >= gameSize)
            fut_y = 0;

        if (grid.getStatus(fut_x,fut_y) == FOOD_BONUS) {
            grow++;
            score++;
            placeBonus(FOOD_BONUS);
        }

        if (grid.getStatus(fut_x,fut_y)  == FOOD_MALUS) {
            grow += 2;
            score--;
        } else if (grid.getStatus(fut_x,fut_y)  == BIG_FOOD_BONUS) {
            grow += 3;
            score += 3;
        }
        movedSnake.setSnake(0, 0, fut_x);
        movedSnake.setSnake(0, 1, fut_y);
        if ((grid.getStatus(movedSnake.getSnake(0, 0), movedSnake.getSnake(0,1)) == SNAKE)) {
            if (snakeMap.size() == 0) {
                game_over = true;
            }
            else {
                snakeMap.remove(movedSnake);
            }
            //game_over = true;
        }

        grid.setStatus(tempx, tempy, EMPTY);
        int snakex, snakey, i;
        for (i = 1; i < gameSize * gameSize; i++) {
            if ((movedSnake.getSnake(i, 0) < 0) || (movedSnake.getSnake(i, 1) < 0)) {
                break;
            }
            grid.setStatus(movedSnake.getSnake(i, 0), movedSnake.getSnake(i, 1), EMPTY);
            snakex = movedSnake.getSnake(i, 0);
            snakey = movedSnake.getSnake(i, 1);
            movedSnake.setSnake(i, 0, tempx);
            movedSnake.setSnake(i, 1, tempy);
            tempx = snakex;
            tempy = snakey;
        }
        for (i = 0; i < gameSize * gameSize; i++) {
            if ((movedSnake.getSnake(i, 0) < 0) || (movedSnake.getSnake(i, 1) < 0)) {
                break;

            }
            grid.setStatus(movedSnake.getSnake(i, 0), movedSnake.getSnake(i, 1), SNAKE);
        }

        bonusTime--;
        if (bonusTime == 0) {
            for (i = 0; i < gameSize; i++) {
                for (int j = 0; j < gameSize; j++) {
                    if (grid.getStatus(i, j) == BIG_FOOD_BONUS)
                        grid.setStatus(i, j, EMPTY);
                }
            }
        }
        malusTime--;
        if (malusTime == 0) {
            for (i = 0; i < gameSize; i++) {
                for (int j = 0; j < gameSize; j++) {
                    if (grid.getStatus(i, j) == FOOD_MALUS)
                        grid.setStatus(i, j, EMPTY);
                }
            }
        }
        if (grow > 0) {
            movedSnake.setSnake(i, 0, tempx);
            movedSnake.setSnake(i, 1, tempy);
            grid.setStatus(movedSnake.getSnake(i, 0), movedSnake.getSnake(i, 1), SNAKE);

            if (score % 10 == 0) {
                placeBonus(BIG_FOOD_BONUS);
                bonusTime = 100;
            }
            if (score % 5 == 0) {
                placeMalus(FOOD_MALUS);
                malusTime = 100;
            }
            grow--;
        }
    }

    private void placeBonus(int bonus_type) {
        int x = (int) (Math.random() * 1000) % gameSize;
        int y = (int) (Math.random() * 1000) % gameSize;
        if (grid.getStatus(x, y) == EMPTY) {
            grid.setStatus(x, y, bonus_type);
        } else {
            placeBonus(bonus_type);
        }
    }

    private void placeMalus(int malus_type) {
        int x = (int) (Math.random() * 1000) % gameSize;
        int y = (int) (Math.random() * 1000) % gameSize;
        if (grid.getStatus(x, y) == EMPTY) {
            grid.setStatus(x, y, malus_type);
        } else {
            placeMalus(malus_type);
        }
    }

    // IMPLEMENTED FUNCTIONS
    public void keyPressed(KeyEvent ke) {
        int code = ke.getKeyCode();
        Dimension dim;
        switch (code) {
            // Controls for Player 1 (snakeMap[0]).
            case KeyEvent.VK_UP:
                if (snakeMap.get(0).getDirection()  != DOWN) {
                    snakeMap.get(0).setNextDirection(UP);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (snakeMap.get(0).getDirection()  != UP) {
                    snakeMap.get(0).setNextDirection(DOWN);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (snakeMap.get(0).getDirection() != RIGHT) {
                    snakeMap.get(0).setNextDirection(LEFT);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (snakeMap.get(0).getDirection() != LEFT) {
                    snakeMap.get(0).setNextDirection(RIGHT);
                }
                break;

            // Controls for Player 2 (snakeMap[1]).
            case KeyEvent.VK_W:
                if (snakeMap.get(1).getDirection() != DOWN) {
                    snakeMap.get(1).setNextDirection(UP);
                }
                break;
            case KeyEvent.VK_S:
                if (snakeMap.get(1).getDirection() != UP) {
                    snakeMap.get(1).setNextDirection(DOWN);
                }
                break;
            case KeyEvent.VK_A:
                if (snakeMap.get(1).getDirection() != RIGHT) {
                    snakeMap.get(1).setNextDirection(LEFT);
                }
                break;
            case KeyEvent.VK_D:
                if (snakeMap.get(1).getDirection() != LEFT) {
                    snakeMap.get(1).setNextDirection(RIGHT);
                }
                break;

            // Controls for Player 3 (snakeMap[2])
            case KeyEvent.VK_Y:
                if (snakeMap.get(2).getDirection() != DOWN) {
                    snakeMap.get(2).setNextDirection(UP);
                }
                break;
            case KeyEvent.VK_H:
                if (snakeMap.get(2).getDirection() != UP) {
                    snakeMap.get(2).setNextDirection(DOWN);
                }
                break;
            case KeyEvent.VK_G:
                if (snakeMap.get(2).getDirection() != RIGHT) {
                    snakeMap.get(2).setNextDirection(LEFT);
                }
                break;
            case KeyEvent.VK_J:
                if (snakeMap.get(2).getDirection() != LEFT) {
                    snakeMap.get(2).setNextDirection(RIGHT);
                }
                break;

            // Controls for Player 4 (snakeMap[3])
            case KeyEvent.VK_O:
                if (snakeMap.get(3).getDirection() != DOWN) {
                    snakeMap.get(3).setNextDirection(UP);
                }
                break;
            case KeyEvent.VK_L:
                if (snakeMap.get(3).getDirection() != UP) {
                    snakeMap.get(3).setNextDirection(DOWN);
                }
                break;
            case KeyEvent.VK_K:
                if (snakeMap.get(3).getDirection() != RIGHT) {
                    snakeMap.get(3).setNextDirection(LEFT);
                }
                break;
            case KeyEvent.VK_COLON:
                if (snakeMap.get(3).getDirection() != LEFT) {
                    snakeMap.get(3).setNextDirection(RIGHT);
                }
                break;

            case KeyEvent.VK_F11:
                dim = Toolkit.getDefaultToolkit().getScreenSize();
                if ((height != dim.height - 50) || (width != dim.height - 50)) {
                    height = dim.height - 50;
                    width = dim.height - 50;
                } else {
                    height = 1000;
                    width = 1000;
                }
                frame.setSize(width + 10, height + 30);
                canvas.setSize(width + 10, height + 30);
                canvas.validate();
                frame.validate();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;

            case KeyEvent.VK_SPACE:
                if (!game_over)
                    paused = !paused;
                break;
            default:
                // Unsupported key
                break;
        }
    }

    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }

    // UNNUSED IMPLEMENTED FUNCTIONS
    public void keyTyped(KeyEvent ke) {
    }

    public void keyReleased(KeyEvent ke) {
    }

    public void windowOpened(WindowEvent we) {
    }

    public void windowClosed(WindowEvent we) {
    }

    public void windowIconified(WindowEvent we) {
    }

    public void windowDeiconified(WindowEvent we) {
    }

    public void windowActivated(WindowEvent we) {
    }

    public void windowDeactivated(WindowEvent we) {
    }
}
