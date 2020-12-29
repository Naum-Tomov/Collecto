import javax.swing.*;

public class CollectoFrame extends JFrame {

    public CollectoFrame() {
        this.add(new CollectoPanel());
        this.setTitle("Collecto");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
