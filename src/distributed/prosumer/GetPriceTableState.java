package distributed.prosumer;

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
    private ProsumerAgent prosumer;
    @Override
    @SuppressWarnings("unchecked")
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = prosumer.blockingReceive(mt);
        GraphicHelper.messages.add(new Message(message));
        if(message.getPerformative() == ACLMessage.INFORM) {
            try {
                System.out.println(prosumer.getAID().getLocalName() + " received price table from " + message.getSender().getLocalName() + ": " + message.getContentObject());
                HashMap<String, ArrayList<Energy>> table = (HashMap<String, ArrayList<Energy>>) message.getContentObject();
                prosumer.getProviders().clear();
                prosumer.getOffers().clear();
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
                        prosumer.getProviders().put(i, bestOfferer);
                        prosumer.getOffers().put(i, bestOffer);
                    }
                }
            } catch (UnreadableException e) {
                System.out.println("Error received from " + this.getClass().getName());
                e.printStackTrace();
            }
        }
    }

    @Override
    public int onEnd() {
        System.out.println("Ended");
        return super.onEnd();
    }


    public GetPriceTableState(ProsumerAgent prosumer) {
        this.prosumer = prosumer;
    }
}
