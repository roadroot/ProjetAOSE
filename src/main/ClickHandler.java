package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import jade.wrapper.AgentController;


public class ClickHandler implements ActionListener {
    JButton button;
    private boolean enabled;
    public ClickHandler(JButton button, AgentController agent) {
        this.button = button;
        this.agent = agent;
    }


    AgentController agent;


    @Override
    public void actionPerformed(ActionEvent e) {
        enabled = !enabled;
        if(enabled) {
            button.setBackground(new Color(255, 255, 255, 0));
        }
        else {
            button.setBackground(new Color(255, 0, 0, 10));
        }
    }
}
