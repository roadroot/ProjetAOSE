package distributed.prosumer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveEnergyState extends CyclicBehaviour {
    public static final String NAME = "receive_energy_state";
    public static final int NONE = 0;
    private int decision = NONE;
    private ProsumerAgent prosumer;
    @Override
    public void action() {
        ACLMessage message = prosumer.blockingReceive(ACLMessage.ACCEPT_PROPOSAL);
        System.out.println(prosumer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContent());
    }

    public ReceiveEnergyState(ProsumerAgent consumer) {
        this.prosumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
