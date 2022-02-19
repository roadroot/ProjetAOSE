package distributed.prosumer;

import java.util.ArrayList;
import java.util.HashMap;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import main.Energy;

public class GetPriceTableState extends OneShotBehaviour {
    public static final String NAME = "get_price_table";
    public static final int NONE = 0;
    private int decision = NONE;
    private ProsumerAgent prosumer;
    @Override
    @SuppressWarnings("unchecked")
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = prosumer.blockingReceive(mt);
        if(message.getPerformative() == ACLMessage.INFORM) {
            try {
                System.out.println(prosumer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContentObject());
                HashMap<String, ArrayList<Energy>> table = (HashMap<String, ArrayList<Energy>>) message.getContentObject();
                for(int i =0; i<prosumer.getConsumption().size(); i++) {
                    String bestOfferer = "";
                    Energy bestOffer = null;
                    for(String offerer: table.keySet())
                        for(Energy offer : table.get(offerer))
                            if(offer.type == prosumer.getConsumption().get(i).type && (bestOffer == null||offer.price<bestOffer.price)) {
                                bestOfferer = offerer;
                                bestOffer = offer;
                            }
                    if(bestOffer != null) {
                        prosumer.getProviders().replace(i, bestOfferer);
                        prosumer.getOffers().replace(i, bestOffer);
                    }
                }
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
        }
    }

    public GetPriceTableState(ProsumerAgent consumer) {
        this.prosumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
