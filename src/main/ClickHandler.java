package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

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
                button.setIcon(image);
            }
            else {
                button.setIcon(null);
            }
        }
    }
}
