package distributed.producer;

import java.io.IOException;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.Energy;
import main.GraphicHelper;

public class InitializationState extends OneShotBehaviour {
    public static final String NAME = "init";
    private int decision = 0;
    private ProducerAgent producer;
    @Override
    public void action() {
        for(Energy energy : producer.getProduction()) {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            try {
                message.setContentObject(energy);
                message.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.producer.send(message);
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
