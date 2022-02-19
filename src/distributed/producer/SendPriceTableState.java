package distributed.producer;

import java.io.IOException;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.Energy;
import main.GraphicHelper;

public class SendPriceTableState extends OneShotBehaviour {
    public static final String NAME = "send_price_table";
    private int decision = 0;
    private ProducerAgent producer;
    @Override
    public void action() {
        ArrayList<Energy> energies= producer.getProduction();
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        try {
            message.setContentObject(energies);
            message.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
            Thread.sleep((long)(Math.random() * 1000));
            this.producer.send(message);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SendPriceTableState(ProducerAgent producer) {
        this.producer = producer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
