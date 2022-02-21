package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;


public class ClickHandler implements ActionListener {
    JButton button;
    private boolean enabled;
    ImageIcon image;
    public ClickHandler(JButton button, AgentController agent, String name, ImageIcon image) {
        this.button = button;
        this.agent = agent;
        this.name = name;
        this.image = image;
        enabled = true;
    }
    String name;

    AgentController agent;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(name)) {
            enabled = !enabled;
            if(enabled) {
                GraphicHelper.addAgent(name);
                ACLMessage message = new ACLMessage(ACLMessage.SUBSCRIBE);
                message.setContent(name);
                message.addReceiver(new AID(name, AID.ISLOCALNAME));
                message.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
                GraphicHelper.agent.send(message);
                button.setIcon(image);
            }
            else {
                GraphicHelper.suspendAgent(name);
                ACLMessage message = new ACLMessage(ACLMessage.CANCEL);
                message.setContent(name);
                message.addReceiver(new AID(name, AID.ISLOCALNAME));
                message.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
                GraphicHelper.agent.send(message);
                button.setIcon(null);
            }
        }
    }
}
