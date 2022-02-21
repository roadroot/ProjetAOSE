package distributed.prosumer;

import distributed.StringConstants;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.GraphicHelper;

public class RequestPriceTableState extends OneShotBehaviour {
    public static final String NAME = "request_price_table";
    private ProsumerAgent prosumer;

    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(StringConstants.GET_PRICE_TABLE);
        message.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
        prosumer.send(message);
    }

    public RequestPriceTableState(ProsumerAgent consumer) {
        this.prosumer = consumer;
    }
}
