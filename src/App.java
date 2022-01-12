import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) throws Exception {
        var conf = new Configuration("conf.json");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        System.out.println("Hello, World!");
        JFrame f=new JFrame();
        GridLayout g = new GridLayout(conf.getWidth(), conf.getHeight());
        f.setLayout(g);
        var buttons = new JButton[conf.getWidth()][conf.getHeight()];
        for(int i =0; i<conf.getWidth(); i++)
            for(int j =0; j<conf.getHeight(); j++) {
                // g.addLayoutComponent(name, comp);
                buttons[i][j] = new JButton();
                buttons[i][j].setSize(conf.getTileWidth(), conf.getTileHeight());
                f.add(buttons[i][j]);
            }
        f.setSize(conf.getSquareWidth(), conf.getSquareHeight());
        f.setVisible(true);
    }
}
