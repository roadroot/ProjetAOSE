package distributed.consumer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.GraphicHelper;
import main.Message;

public class ReceiveEnergyState extends CyclicBehaviour {
    public static final String NAME = "receive_energy_state";
    public static final int NONE = 0;
    private int decision = NONE;
    private ConsumerAgent consumer;
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
        ACLMessage message = consumer.blockingReceive(mt);
        GraphicHelper.messages.add(new Message(message));
        System.out.println(consumer.getAID().getLocalName() + " received energy from " + message.getSender().getLocalName() + ": " + message.getContent());
    }

    public ReceiveEnergyState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
