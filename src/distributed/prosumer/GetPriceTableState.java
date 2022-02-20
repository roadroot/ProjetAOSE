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
    private ProsumerAgent prosumer;
    @Override
    @SuppressWarnings("unchecked")
    public void action() {
        System.out.println("Started");
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = prosumer.blockingReceive(mt);
        if(message.getPerformative() == ACLMessage.INFORM) {
            try {
                System.out.println(prosumer.getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContentObject());
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
        System.out.println("Ended");
    }

    @Override
    public int onEnd() {
        System.out.println("#############################################################");
        return super.onEnd();
    }


    public GetPriceTableState(ProsumerAgent prosumer) {
        this.prosumer = prosumer;
    }
}
