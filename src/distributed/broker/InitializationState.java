package distributed.broker;

import java.util.HashMap;

import distributed.StringConstants;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.GraphicHelper;
import main.Message;

public class InitializationState extends OneShotBehaviour {
    public static final String NAME = "init";
    public static final int NONE = 0;
    public static final int GET_TABLES = 1;
    private int decision = NONE;
    private BrokerAgent broker;
    @Override
    public void action() {
        decision = NONE;
        ACLMessage message = broker.blockingReceive();
        GraphicHelper.messages.add(new Message(message));
        System.out.println(broker.getAID().getLocalName() + " received from " + message.getSender().getLocalName() + ": " + message.getContent());
        if(message.getPerformative() == ACLMessage.REQUEST && message.getContent().equals(StringConstants.GET_PRICE_TABLE)) {
            broker.tableRequest = message.getSender();
            if(broker.table == null || broker.table.size() <= GraphicHelper.getProducers().size() + GraphicHelper.getProsumers().size()) {
                for(String agent : GraphicHelper.getProducers()) {
                    ACLMessage requestMessage = new ACLMessage(ACLMessage.REQUEST);
                    requestMessage.addReceiver(new AID(agent, AID.ISLOCALNAME));
                    requestMessage.setContent(StringConstants.GET_PRICE_TABLE);
                    broker.send(requestMessage);
                }
                for(String agent : GraphicHelper.getProsumers()) {
                    ACLMessage requestMessage = new ACLMessage(ACLMessage.REQUEST);
                    requestMessage.addReceiver(new AID(agent, AID.ISLOCALNAME));
                    requestMessage.setContent(StringConstants.GET_PRICE_TABLE);
                    broker.send(requestMessage);
                }
                broker.table = new HashMap<>();
            }
            decision = GET_TABLES;
        } else if(message.getPerformative() == ACLMessage.CANCEL) {
            if(GraphicHelper.agentTypes.get(message.getContent()) != 1) {
                broker.table = new HashMap<>();
                for(String agent : GraphicHelper.getConsumers()) {
                    ACLMessage reply = new ACLMessage(ACLMessage.CANCEL);
                    reply.setContent(message.getContent());
                    reply.addReceiver(new AID(agent, AID.ISLOCALNAME));
                    broker.send(reply);
                }
                for(String agent : GraphicHelper.getProsumers()) {
                    ACLMessage reply = new ACLMessage(ACLMessage.CANCEL);
                    reply.setContent(message.getContent());
                    reply.addReceiver(new AID(agent, AID.ISLOCALNAME));
                    broker.send(reply);
                }
            }
        } else if(message.getPerformative() == ACLMessage.SUBSCRIBE) {
            if(GraphicHelper.agentTypes.get(message.getContent()) != 1){
                broker.table = new HashMap<>();
                for(String agent : GraphicHelper.getConsumers()) {
                    ACLMessage reply = new ACLMessage(ACLMessage.SUBSCRIBE);
                    reply.setContent(message.getContent());
                    reply.addReceiver(new AID(agent, AID.ISLOCALNAME));
                    broker.send(reply);
                }
                for(String agent : GraphicHelper.getProsumers()) {
                    ACLMessage reply = new ACLMessage(ACLMessage.SUBSCRIBE);
                    reply.setContent(message.getContent());
                    reply.addReceiver(new AID(agent, AID.ISLOCALNAME));
                    broker.send(reply);
                }
            }
        }
    }

    public InitializationState(BrokerAgent broker) {
        this.broker = broker;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
