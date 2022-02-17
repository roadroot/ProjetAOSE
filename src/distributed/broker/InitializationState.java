package distributed.broker;

import distributed.StringConstants;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.GraphicHelper;

public class InitializationState extends OneShotBehaviour {
    public static final String NAME = "init";
    public static final int NONE = 0;
    public static final int GET_TABLES = 1;
    private int decision = NONE;
    private BrokerAgent broker;
    @Override
    public void action() {
        broker.doWait();
        ACLMessage message = broker.receive();
        System.out.println(message.getContent());
        if(message.getPerformative() == ACLMessage.REQUEST && message.getContent().equals(StringConstants.GET_PRICE_TABLE)) {
            ACLMessage requestMessage = new ACLMessage(ACLMessage.REQUEST);
            requestMessage.setContent(StringConstants.GET_PRICE_TABLE);
            for(String agent : GraphicHelper.getProducers())
                requestMessage.addReceiver(new AID(agent, AID.ISLOCALNAME));
            broker.send(requestMessage);
            decision = GET_TABLES;
        }
        // try {
        //     System.out.println(broker.receive().getContentObject());
        // } catch (UnreadableException e) {
        //     e.printStackTrace();
        // }
    }

    public InitializationState(BrokerAgent broker) {
        this.broker = broker;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
