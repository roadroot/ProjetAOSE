package distributed.producer;

import java.io.IOException;
import java.util.ArrayList;

import distributed.ProsumerAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.Energy;
import main.GraphicHelper;

public class SendPriceTableState extends OneShotBehaviour {
    public static final String NAME = "send_price_table";
    private int decision = 0;
    private Agent agent;
    @Override
    public void action() {
        ArrayList<Energy> energies=null;
        if(agent instanceof ProducerAgent)
            energies = ((ProducerAgent) agent).getProduction();
        else if(agent instanceof ProducerAgent)
            energies = ((ProsumerAgent) agent).getProduction();
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        try {
            message.setContentObject(energies);
            message.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.agent.send(message);
    }

    public SendPriceTableState(ProducerAgent producer) {
        this.agent = producer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
