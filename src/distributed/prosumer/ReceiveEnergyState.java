package distributed.prosumer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveEnergyState extends CyclicBehaviour {
    public static final String NAME = "receive_energy_state";
    private ProsumerAgent prosumer;
    @Override
    public void action() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        ACLMessage message = prosumer.blockingReceive(ACLMessage.ACCEPT_PROPOSAL);
        System.out.println(prosumer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContent());
    }

    public ReceiveEnergyState(ProsumerAgent consumer) {
        this.prosumer = consumer;
    }
}
