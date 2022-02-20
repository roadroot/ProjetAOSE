package distributed.producer;

import java.io.IOException;
import java.util.ArrayList;

import distributed.StringConstants;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import main.Energy;
import main.GraphicHelper;

public class InitializationState extends CyclicBehaviour {
    public static final String NAME = "init";
    private ProducerAgent producer;
    @Override
    public void action() {
        ACLMessage message = producer.blockingReceive();
        if(message.getPerformative() == ACLMessage.REQUEST && message.getContent().equals(StringConstants.GET_PRICE_TABLE)) {
            System.out.println(producer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContent());
            ArrayList<Energy> energies= producer.getProduction();
            ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
            try {
                reply.setContentObject(energies);
                reply.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
                this.producer.send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
        if(message.getPerformative() == ACLMessage.PROPOSE) {
            try {
                System.out.println(producer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContentObject());
                Energy energy = (Energy) message.getContentObject();
                boolean valid = false;
                for(Energy prod:producer.getProduction()) {
                    if(prod.getType() == energy.getType() && prod.price == energy.price) {
                        valid = true;
                        break;
                    }
                }
                if(valid) {
                    ACLMessage reply = message.createReply();
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    reply.setContent(energy.toString());
                    producer.send(reply);
                }
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(".?.??..??.?.?.??.............???????????.?.................?..........?.?.?.?");
            System.out.println(producer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContent());
            System.err.println(".?.??..??.?.?.??.............???????????.?.................?..........?.?.?.?");
        }
    }

    public InitializationState(ProducerAgent producer) {
        this.producer = producer;
    }
}
