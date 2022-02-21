package main;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;

public class App {
    public static void main(String[] args) throws Exception {
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8755, null);
        config.setParameter("gui", "false");
        AgentContainer mc = runtime.createMainContainer(config);
        mc.createNewAgent("GraphicHelper", GraphicHelper.class.getName(), null).start();
        String file = "conf.json";
        if(args.length == 1)
            file = args[0];
        Configuration conf = new Configuration(file, mc);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame f=new JFrame();
        GridLayout g = new GridLayout(conf.getWidth(), conf.getHeight());
        f.setLayout(g);
        JButton[][] buttons = new JButton[conf.getWidth()][conf.getHeight()];
        for(int i=0; i<conf.getWidth(); i++)
            for(int j =0; j<conf.getHeight(); j++) {
                // g.addLayoutComponent(name, comp);
                buttons[i][j] = new JButton();
                buttons[i][j].setEnabled(false);
                buttons[i][j].setSize(conf.getTileWidth(), conf.getTileHeight());
                f.add(buttons[i][j]);
            }

        conf.getAgents().get(GraphicHelper.getBroker()).start();

        for(String agent : GraphicHelper.getProducers()) {
            ImageIcon image = new ImageIcon((new ImageIcon("resources/producer.png")).getImage().getScaledInstance(conf.getTileWidth()*3/4, conf.getTileHeight()*3/4, 0));
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setIcon(image);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setEnabled(true);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setActionCommand(agent);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].addActionListener(new ClickHandler(buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()], conf.getAgents().get(agent), agent, image));
            conf.getAgents().get(agent).start();
        }
        for(String agent : GraphicHelper.getProsumers()) {
            ImageIcon image = new ImageIcon((new ImageIcon("resources/prosumer.png")).getImage().getScaledInstance(conf.getTileWidth()*3/4, conf.getTileHeight()*3/4, 0));
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setIcon(image);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setEnabled(true);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setActionCommand(agent);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].addActionListener(new ClickHandler(buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()], conf.getAgents().get(agent), agent, image));
            conf.getAgents().get(agent).start();

        }
        for(String agent : GraphicHelper.getConsumers()) {
            ImageIcon image = new ImageIcon((new ImageIcon("resources/consumer.png")).getImage().getScaledInstance(conf.getTileWidth()*3/4, conf.getTileHeight()*3/4, 0));
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setIcon(image);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setEnabled(true);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].setActionCommand(agent);
            buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()].addActionListener(new ClickHandler(buttons[GraphicHelper.positions.get(agent).getX()][GraphicHelper.positions.get(agent).getY()], conf.getAgents().get(agent), agent, image));
            conf.getAgents().get(agent).start();
        }
        f.setSize(conf.getSquareWidth(), conf.getSquareHeight());
        f.setVisible(true);
    }
}
