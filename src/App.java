import javax.swing.*;
import java.awt.*;

abstract class Configuration {
    static final int N = 5;
    static final int M = 5;
    static final int TILE_WIDTH = 60;
    static final int TILE_HEIGHT = 60;
    static int getWidth() {
        return N * TILE_WIDTH;
    }
    static int getHeight() {
        return M * TILE_HEIGHT;
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        System.out.println("Hello, World!");
        JFrame f=new JFrame();
        GridLayout g = new GridLayout(Configuration.N, Configuration.M);
        f.setLayout(g);
        for(int i =0; i<Configuration.N; i++)
            for(int j =0; j<Configuration.M; j++) {
                // g.addLayoutComponent(name, comp);
                var button = new JButton(i + " " + j);
                button.setSize(Configuration.TILE_WIDTH, Configuration.TILE_HEIGHT);
                f.add(button);
            }
        f.setSize(Configuration.getWidth(), Configuration.getHeight());
        f.setVisible(true);
    }
}
