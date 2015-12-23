/**
 * Created by 777 on 12/23/2015.
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game {

    public static final int UPS_CAP = 60;
    public static final int NUM_BUFFERS = 2;
    public static final String TITLE = "Game of Life";
    public static final int WIDTH = 100;
    public static final int HEIGHT = 70;
    public static final boolean RESIZABLE = false;

    private static boolean running = false;
    private static JFrame window;
    private static Canvas c;
    private static BufferStrategy buffer;
    private static Graphics2D g;

    private static boolean cellSelection = true;
    private static Cell[] cells;
    private static long lastCellUpdate = 0;

    public static void main(String[] args) {
        initWindow();
        initGame();
        run();
    }

    private static void initWindow() {
        window = new JFrame();
        c = new Canvas();
        window.setTitle(TITLE);
        window.setResizable(RESIZABLE);
        c.setSize(WIDTH * Cell.CELL_SIZE, HEIGHT * Cell.CELL_SIZE);
        window.add(c);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setVisible(true);
        c.createBufferStrategy(NUM_BUFFERS);
        Keyboard.create(c);
        Mouse.create(c);
        JOptionPane
                .showMessageDialog(
                        null,
                        "Click to select points to start with, then press enter to start cellular automata. To restart, close program and start again.");
    }

    private static void initGame() {
        running = true;
        cells = new Cell[WIDTH * HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                cells[x + (y * WIDTH)] = new Cell(new Location(x, y), false);
            }
        }
    }

    private static void run() {

        final double UNS = 1e9 / UPS_CAP;
        long lastFrame = System.nanoTime();
        double delta = 0;
        int frames = 0;
        int updates = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long thisFrame = System.nanoTime();
            delta += (thisFrame - lastFrame) / UNS;
            lastFrame = thisFrame;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer >= 1e3) {
                timer += 1e3;
                window.setTitle("Game of Life | FPS: " + frames + " UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private static void update() {
        if (cellSelection) {
            // mouse input
            if (Mouse.isButtonDown(1)) {
                int cellX = Mouse.getCellX();
                int cellY = Mouse.getCellY();
                cells[cellX + (cellY * WIDTH)].setAlive(true);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_ENTER)) {
                cellSelection = false;
            }
        } else {
            // update cells
            if (System.currentTimeMillis() - lastCellUpdate >= 250) {
                for (Cell cell : cells) {
                    cell.update();
                }
                lastCellUpdate = System.currentTimeMillis();
            }
        }
    }

    private static void render() {
        buffer = c.getBufferStrategy();
        g = (Graphics2D) buffer.getDrawGraphics();
        {
            // render
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, window.getWidth(), window.getHeight());
            renderGrid();
            renderCells();
        }
        g.dispose();
        buffer.show();
    }

    private static void renderGrid() {
        for (int x = 0; x < WIDTH; x++) {
            g.setColor(Color.BLACK);
            g.drawLine(x * Cell.CELL_SIZE, 0, x * Cell.CELL_SIZE, WIDTH * Cell.CELL_SIZE);
        }
        for (int y = 0; y < HEIGHT; y++) {
            g.setColor(Color.BLACK);
            g.drawLine(0, y * Cell.CELL_SIZE, WIDTH * Cell.CELL_SIZE, y * Cell.CELL_SIZE);
        }
    }

    private static void renderCells() {
        for (Cell cell : cells) {
            cell.render();
        }
    }

    public static Graphics2D getGraphics() {
        return g;
    }

    public static Cell[] getCells() {
        return cells;
    }

    public static Cell getCellAt(int x, int y) {
        return cells[x + (y * WIDTH)];
    }
}

