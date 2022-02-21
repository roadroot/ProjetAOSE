package distributed.consumer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.GraphicHelper;
import main.Message;

public class SuspendedState extends OneShotBehaviour {
    public static final String NAME = "Suspended State";
    private ConsumerAgent consumer;
    @Override
    public void action() {
        if(consumer.suspended) {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE);
            ACLMessage message = consumer.blockingReceive(mt);
            GraphicHelper.messages.add(new Message(message));
            consumer.suspended = false;
            System.out.println(consumer.getAID().getLocalName() + " is unsuspended by " + message.getSender().getLocalName());
        }
    }

    public SuspendedState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }
}
