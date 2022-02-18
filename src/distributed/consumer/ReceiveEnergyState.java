package distributed.consumer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveEnergyState extends OneShotBehaviour {
    public static final String NAME = "receive_energy_state";
    public static final int NONE = 0;
    private int decision = NONE;
    private ConsumerAgent consumer;
    @Override
    public void action() {
        consumer.doWait();
        ACLMessage message = consumer.receive();
        System.out.println(consumer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContent());

    }

    public ReceiveEnergyState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
