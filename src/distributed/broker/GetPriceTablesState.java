package distributed.broker;

import java.util.ArrayList;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import main.Energy;
import main.GraphicHelper;
import main.Message;

public class GetPriceTablesState extends OneShotBehaviour {
    public static final String NAME = "get_price_tables";
    public static final int NONE = 0;
    public static final int SEND_TABLE = 1;
    public static final int GET_OTHER = 2;
    private int decision = NONE;
    private BrokerAgent broker;
    @Override
    @SuppressWarnings("unchecked")
    public void action() {
        if(broker.table != null && broker.table.size() <= GraphicHelper.getProducers().size() + GraphicHelper.getProsumers().size()) {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage message = broker.blockingReceive(mt);
            GraphicHelper.messages.add(new Message(message));
            try {
                System.out.println(broker.getAID().getLocalName() + ": received price table from: " + message.getSender().getLocalName() + " " + message.getContentObject());
                broker.table.put(message.getSender().getLocalName(), (ArrayList<Energy>) message.getContentObject());

            } catch (UnreadableException e) {
                System.out.println("Error received from " + this.getClass().getName());
                e.printStackTrace();
            }
        }
        if(broker.table.keySet().size() == GraphicHelper.getProducers().size() + GraphicHelper.getProsumers().size())
            decision = SEND_TABLE;
        else
            decision = GET_OTHER;

    }

    public GetPriceTablesState(BrokerAgent broker) {
        this.broker = broker;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
