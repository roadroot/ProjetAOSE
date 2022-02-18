package distributed.producer;

import distributed.StringConstants;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import main.Energy;

public class InitializationState extends OneShotBehaviour {
    public static final int DECISION_NONE = 0;
    public static final int DECISION_SEND_TABLE = 1;
    public static final int DECISION_SEND_ENERGY = 2;
    public static final String NAME = "init";
    private int decision = DECISION_NONE;
    private ProducerAgent producer;
    @Override
    public void action() {
        producer.doWait();
        ACLMessage message = producer.receive();
        if(message.getPerformative() == ACLMessage.REQUEST && message.getContent().equals(StringConstants.GET_PRICE_TABLE)) {
            System.out.println(producer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContent());
            decision = DECISION_SEND_TABLE;
        }
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
        }
    }

    public InitializationState(ProducerAgent producer) {
        this.producer = producer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
