package main;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class Message {
    public ACLMessage message;

    public Message(ACLMessage message) {
        this.message = message;
    }

    public String getSender() {
        return message.getSender().getLocalName();
    }

    public String getReceiver() {
        return ((AID)message.getAllReceiver().next()).getLocalName();
    }

    public String getContent() {
        return message.getContent();
    }
}
