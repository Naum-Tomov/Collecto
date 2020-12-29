import java.util.ArrayList;

public class Player {
    private ArrayList<Ball> balls;
    private int score;
    private String name;

    public Player(String name) {
        this.name = name;
        balls = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBall (Ball ball) {
        balls.add(ball);
    }

}
