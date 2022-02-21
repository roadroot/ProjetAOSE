package distributed.consumer;

import java.util.ArrayList;
import java.util.HashMap;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import main.Energy;
import main.GraphicHelper;
import main.Message;

public class GetPriceTableState extends OneShotBehaviour {
    public static final String NAME = "get_price_table";
    public static final int NONE = 0;
    private int decision = NONE;
    private ConsumerAgent consumer;
    @Override
    @SuppressWarnings("unchecked")
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = consumer.blockingReceive(mt);
        GraphicHelper.messages.add(new Message(message));
        try {
            System.out.println(consumer.getAID().getLocalName() + " received complete price table from " + message.getSender().getLocalName() + " " + message.getContentObject());
            consumer.table = (HashMap<String, ArrayList<Energy>>) message.getContentObject();
            for(int i =0; i<consumer.getConsumption().size(); i++) {
                String bestOfferer = "";
                Energy bestOffer = null;
                for(String offerer: consumer.table.keySet())
                    for(Energy offer : consumer.table.get(offerer))
                        if(offer.type == consumer.getConsumption().get(i).type && (bestOffer == null||offer.price<bestOffer.price)) {
                            bestOfferer = offerer;
                            bestOffer = offer;
                        }
                if(bestOffer != null) {
                    consumer.getProviders().replace(i, bestOfferer);
                    consumer.getOffers().replace(i, bestOffer);
                }
            }
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
    }

    public GetPriceTableState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
