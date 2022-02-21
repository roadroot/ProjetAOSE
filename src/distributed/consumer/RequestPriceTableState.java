package distributed.consumer;

import distributed.StringConstants;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.GraphicHelper;

public class RequestPriceTableState extends OneShotBehaviour {
    public static final String NAME = "request_price_table";
    public static final int NONE = 0;
    public static final int GET_PROVIDER = 1;
    private int decision = NONE;
    private ConsumerAgent consumer;

    @Override
    public void action() {
        consumer.done = false;
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(StringConstants.GET_PRICE_TABLE);
        message.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
        consumer.send(message);
    }

    public RequestPriceTableState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
