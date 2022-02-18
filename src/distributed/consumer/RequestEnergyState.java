package distributed.consumer;

import java.io.IOException;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.Energy;

public class RequestEnergyState extends OneShotBehaviour {
    public static final String NAME = "request_energy_state";
    public static final int NONE = 0;
    private int decision = NONE;
    private ConsumerAgent consumer;
    @Override
    public void action() {
        for(int i = 0; i<consumer.getOffers().size(); i++) {
            if(consumer.getOffers().get(i) == null || consumer.getProviders().get(i) == null) continue;
            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(consumer.getProviders().get(i), AID.ISLOCALNAME));
            Energy offer = new Energy(consumer.getConsumption().get(i).amount, consumer.getConsumption().get(i).type, consumer.getOffers().get(i).price);
            try {
                message.setContentObject(offer);
                consumer.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public RequestEnergyState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
