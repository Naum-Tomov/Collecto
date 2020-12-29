import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class CollectoPanel extends JPanel implements ActionListener {

    static final int WIDTH = 770;
    static final int HEIGHT = 770;
    static final int UNIT_SIZE = WIDTH/7;
    static final int GAME_UNITS = (WIDTH*HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    Ball[] balls = new Ball[GAME_UNITS];

    CollectoPanel(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }


    public void startGame() {
        for (int i = 0; i < 8; i++) {
            balls[i] = Ball.BLUE;
            balls[i+8] = Ball.GREEN;
            balls[i+16] = Ball.PINK;
            balls[i+24] = Ball.RED;
            balls[i+32] = Ball.YELLOW;
            balls[i+40] = Ball.ORANGE;
        }
        balls[GAME_UNITS - 1] = Ball.EMPTY;
        while(adjacentColors()) {
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

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        System.out.println(Arrays.toString(balls));
        System.out.println(adjacentColors());
        for(int i=0;i<HEIGHT/UNIT_SIZE;i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, WIDTH, i*UNIT_SIZE);
        }
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
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
                g.fillOval(row*UNIT_SIZE + UNIT_SIZE/4, col*UNIT_SIZE + UNIT_SIZE/4, UNIT_SIZE/2, UNIT_SIZE/2);
            }
        }
    }


    public boolean adjacentColors() {
        boolean result = false;
        for (int i=0; i < GAME_UNITS - 1; i++) {
            boolean adj = false;
            if (balls[i].equals(balls[i+1]) && (i%7) != 6) {
                adj = true;
            }
            if (i < 42 && balls[i].equals(balls[i+7])) {
                adj = true;
            }
            result = result || adj;
        }
        return result;
    }

    public void swap(int index1, int index2) {
        Ball temp = balls[index1];
        balls[index1] = balls[index2];
        balls [index2] = temp;
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }
}
