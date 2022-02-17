package distributed.broker;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GetPriceTablesState extends OneShotBehaviour {
    public static final String NAME = "get_price_tables";
    public static final int NONE = 0;
    private int decision = NONE;
    private BrokerAgent broker;
    @Override
    public void action() {
        broker.doWait();
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = broker.receive(mt);
        System.out.println(message.getContent());
    }

    public GetPriceTablesState(BrokerAgent broker) {
        this.broker = broker;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
