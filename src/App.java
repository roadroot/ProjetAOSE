import java.awt.GridLayout;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class App {
    public static void main(String[] args) throws Exception {
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8888, null);
        config.setParameter("gui", "true");
        AgentContainer mc = runtime.createMainContainer(config);
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

        {
            Map.Entry<String, BrokerAgent> entry = conf.getBrokerAgent();
            AgentController ac = mc.createNewAgent(entry.getKey(), BrokerAgent.class.getName(), null);
            ac.start();
        }

        for(Map.Entry<String, ProducerAgent> entry : conf.getProducerAgents().entrySet()) {
            buttons[entry.getValue().getPositionX()][entry.getValue().getPositionY()].setIcon(new ImageIcon((new ImageIcon("resources/tile004.png")).getImage().getScaledInstance(conf.getTileWidth()*3/4, conf.getTileHeight()*3/4, 0)));
            AgentController ac = mc.createNewAgent(entry.getKey(), ProducerAgent.class.getName(), null);
            ac.start();
        }
        for(Map.Entry<String, ProsumerAgent> entry : conf.getProsumerAgents().entrySet()) {
            buttons[entry.getValue().getPositionX()][entry.getValue().getPositionY()].setIcon(new ImageIcon((new ImageIcon("resources/tile004.png")).getImage().getScaledInstance(conf.getTileWidth()*3/4, conf.getTileHeight()*3/4, 0)));
            AgentController ac = mc.createNewAgent(entry.getKey(), ProsumerAgent.class.getName(), null);
            ac.start();
        }
        for(Map.Entry<String, ConsumerAgent> entry : conf.getConsumerAgents().entrySet()) {
            buttons[entry.getValue().getPositionX()][entry.getValue().getPositionY()].setIcon(new ImageIcon((new ImageIcon("resources/tile004.png")).getImage().getScaledInstance(conf.getTileWidth()*3/4, conf.getTileHeight()*3/4, 0)));
            AgentController ac = mc.createNewAgent(entry.getKey(), ConsumerAgent.class.getName(), null);
            ac.start();
        }
        f.setSize(conf.getSquareWidth(), conf.getSquareHeight());
        f.setVisible(true);
    }
}
