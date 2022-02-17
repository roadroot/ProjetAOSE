package distributed.consumer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class GetPriceTableState extends OneShotBehaviour {
    public static final String NAME = "get_price_table";
    public static final int NONE = 0;
    private int decision = NONE;
    private ConsumerAgent consumer;
    @Override
    public void action() {
        consumer.doWait();
        ACLMessage message = consumer.receive();
        if(message.getPerformative() == ACLMessage.INFORM) {
            try {
                System.out.println(message.getContentObject());
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
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
