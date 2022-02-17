package distributed.producer;

import distributed.StringConstants;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class InitializationState extends OneShotBehaviour {
    public static final int DECISION_NONE = 0;
    public static final int DECISION_SEND_TABLE = 1;
    public static final String NAME = "init";
    private int decision = DECISION_NONE;
    private ProducerAgent producer;
    @Override
    public void action() {
        producer.doWait();
        ACLMessage message = producer.receive();
        System.out.println(message.getContent());
        if(message.getPerformative() == ACLMessage.REQUEST && message.getContent().equals(StringConstants.GET_PRICE_TABLE)) {
            decision = DECISION_SEND_TABLE;
        }
    }

    public InitializationState(ProducerAgent producer) {
        this.producer = producer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
