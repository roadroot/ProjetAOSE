package distributed.prosumer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.GraphicHelper;
import main.Message;

public class ReceiveEnergyState extends OneShotBehaviour {
    public static final String NAME = "receive_energy_state";
    private ProsumerAgent prosumer;
    @Override
    public void action() {
        ACLMessage message = prosumer.blockingReceive(ACLMessage.ACCEPT_PROPOSAL);
        GraphicHelper.messages.add(new Message(message));
        System.out.println(prosumer.getAID().getLocalName() + " received energy from: " + message.getSender().getLocalName() + ": " + message.getContent());
    }

    public ReceiveEnergyState(ProsumerAgent consumer) {
        this.prosumer = consumer;
    }
}
