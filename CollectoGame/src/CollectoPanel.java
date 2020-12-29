import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CollectoPanel extends JPanel {

    static final int WIDTH = 770;
    static final int HEIGHT = 770;
    static final int UNIT_SIZE = WIDTH/7;
    static final int GAME_UNITS = (WIDTH*HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    Ball[] balls = new Ball[GAME_UNITS];
    static int selectedRow = 10;
    static int selectedCol = 10;
    Player[] players = new Player[2];
    static int currentPlayer;
    static Set<Integer> highlightedBalls;

    CollectoPanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addMouseListener(new MyMouseAdapter());
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }


    public void startGame() {
        highlightedBalls = new HashSet<>();
        Random random = new Random();
        currentPlayer = random.nextInt(2);
        for (int i = 0; i < 8; i++) {
            balls[i] = Ball.BLUE;
            balls[i+8] = Ball.GREEN;
            balls[i+16] = Ball.PINK;
            balls[i+24] = Ball.RED;
            balls[i+32] = Ball.YELLOW;
            balls[i+40] = Ball.ORANGE;
        }
        balls[GAME_UNITS - 1] = Ball.EMPTY;
        while(!adjacentColors().isEmpty()) {
            List<Ball> ballList = Arrays.asList(balls);
            Collections.shuffle(ballList);
            ballList.toArray(balls);
            int emptyIndex = 48;
            for (int i = 0; i < GAME_UNITS; i++) {
                if (balls[i] == Ball.EMPTY) {
                    emptyIndex = i;
                }
            }
            swap(24, emptyIndex);
        }
        players[0] = new Player("zero");
        players[1] = new Player("one");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for(int i=0;i<HEIGHT/UNIT_SIZE;i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, WIDTH, i*UNIT_SIZE);
        }
        g.setColor(Color.GRAY);
        if (selectedRow < 10 && selectedCol < 10) {
            for (int i = 0; i < 7; i++) {
                g.fillRect(selectedCol*UNIT_SIZE, i*UNIT_SIZE , UNIT_SIZE, UNIT_SIZE);
                g.fillRect(i*UNIT_SIZE, selectedRow*UNIT_SIZE , UNIT_SIZE, UNIT_SIZE);
            }
        }
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                if (highlightedBalls.contains(row*7 + col)) {
                    g.setColor(Color.WHITE);
                    g.fillRect(col*UNIT_SIZE, row*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
                switch (balls[row*7 + col]) {
                    case EMPTY:
                        g.setColor(Color.BLACK);
                        break;
                    case YELLOW:
                        g.setColor(Color.YELLOW);
                        break;
                    case BLUE:
                        g.setColor(Color.BLUE);
                        break;
                    case RED:
                        g.setColor(Color.RED);
                        break;
                    case PINK:
                        g.setColor(new Color(255,105,180));
                        break;
                    case GREEN:
                        g.setColor(new Color(50,205,50));
                        break;
                    case ORANGE:
                        g.setColor(new Color(255, 130, 0));
                        break;
                    default:
                        break;
                }
                g.fillOval(col*UNIT_SIZE + UNIT_SIZE/4, row*UNIT_SIZE + UNIT_SIZE/4, UNIT_SIZE/2, UNIT_SIZE/2);
            }
        }
    }

    public void moveColUp(int colIndex) {
        ArrayList<Ball> ballsInCol = new ArrayList<>();
        for (int i = colIndex; i < 49; i += 7) {
            if (!balls[i].equals(Ball.EMPTY)) {
                ballsInCol.add(balls[i]);
            }
            balls[i] = Ball.EMPTY;
        }
        int firstIndexInCol = colIndex;
        for (Ball ball : ballsInCol) {
            balls[firstIndexInCol] = ball;
            firstIndexInCol += 7;
        }
    }
    public void moveColDown(int colIndex) {
        ArrayList<Ball> ballsInCol = new ArrayList<>();
        for (int i = colIndex + 42; i >= 0; i -= 7) {
            if (!balls[i].equals(Ball.EMPTY)) {
                ballsInCol.add(balls[i]);
            }
            balls[i] = Ball.EMPTY;
        }
        int lastIndexInCol = colIndex + 42;
        for (Ball ball : ballsInCol) {
            balls[lastIndexInCol] = ball;
            lastIndexInCol -= 7;
        }
    }

    public void moveRowRight(int rowIndex) {
        ArrayList<Ball> ballsInRow = new ArrayList<>();
        for (int i = rowIndex*7 + 6; i >= rowIndex*7; i--) {
            if (!balls[i].equals(Ball.EMPTY)) {
                ballsInRow.add(balls[i]);
            }
            balls[i] = Ball.EMPTY;
        }
        int lastIndexInRow = rowIndex*7 + 6;
        for (Ball ball : ballsInRow) {
            balls[lastIndexInRow] = ball;
            lastIndexInRow--;
        }
    }


    public void moveRowLeft(int rowIndex) {
        ArrayList<Ball> ballsInRow = new ArrayList<>();
        for (int i = rowIndex*7; i < rowIndex*7 + 7; i++) {
            if (!balls[i].equals(Ball.EMPTY)) {
                ballsInRow.add(balls[i]);
            }
            balls[i] = Ball.EMPTY;
        }
        int firstIndexInRow = rowIndex*7;
        for (Ball ball : ballsInRow) {
            balls[firstIndexInRow] = ball;
            firstIndexInRow++;
        }
    }

    public Set<Integer> adjacentColors() {
        Set<Integer> result = new HashSet<>();
        for (int i=0; i < GAME_UNITS - 1; i++) {
            if (balls[i].equals(balls[i+1]) && (i%7) != 6 && !balls[i].equals(Ball.EMPTY)) {
                result.add(i);
                result.add(i+1);
            }
            if (i < 42 && balls[i].equals(balls[i+7]) && !balls[i].equals(Ball.EMPTY)) {
                result.add(i);
                result.add(i+7);
            }
        }
        return result;
    }

    public void swap(int index1, int index2) {
        Ball temp = balls[index1];
        balls[index1] = balls[index2];
        balls [index2] = temp;
    }

    public void collect() {
        highlightedBalls = adjacentColors();
        for (int index : highlightedBalls) {
            players[currentPlayer].addBall(balls[index]);
            balls[index] = Ball.EMPTY;
        }
        currentPlayer = currentPlayer == 0 ? 1 : 0;
    }


    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            highlightedBalls = new HashSet<>();
            int x=e.getX();
            selectedCol = x/UNIT_SIZE;
            int y=e.getY();
            selectedRow = y/UNIT_SIZE;
            repaint();
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveRowLeft(selectedRow);
                    collect();
                    repaint();
                    break;
                case KeyEvent.VK_RIGHT:
                    moveRowRight(selectedRow);
                    collect();
                    repaint();
                    break;
                case KeyEvent.VK_UP:
                    moveColUp(selectedCol);
                    collect();
                    repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    moveColDown(selectedCol);
                    collect();
                    repaint();
                    break;
            }
        }
    }
}
