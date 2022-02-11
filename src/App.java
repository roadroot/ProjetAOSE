import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration("conf.json");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame f=new JFrame();
        GridLayout g = new GridLayout(conf.getWidth(), conf.getHeight());
        f.setLayout(g);
        JButton[][] buttons = new JButton[conf.getWidth()][conf.getHeight()];
        for(int i=0; i<conf.getWidth(); i++)
            for(int j =0; j<conf.getHeight(); j++) {
                // g.addLayoutComponent(name, comp);
                buttons[i][j] = new JButton();
                buttons[i][j].setSize(conf.getTileWidth(), conf.getTileHeight());
                f.add(buttons[i][j]);
            }
        for(ParentAgent v : conf.getAgents()) {
            buttons[v.getPositionX()][v.getPositionY()].setIcon(new ImageIcon((new ImageIcon("resources/tile004.png")).getImage().getScaledInstance(conf.getTileWidth()*3/4, conf.getTileHeight()*3/4, 0)));
        }
        f.setSize(conf.getSquareWidth(), conf.getSquareHeight());
        f.setVisible(true);
    }
}
