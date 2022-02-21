package distributed.consumer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import main.GraphicHelper;
import main.Message;

public class ReceiveEnergyState extends Behaviour {
    public static final String NAME = "receive_energy_state";
    private ConsumerAgent consumer;
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE), MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchPerformative(ACLMessage.CANCEL)));
        ACLMessage message = consumer.blockingReceive(mt);
        GraphicHelper.messages.add(new Message(message));
        if(message.getPerformative() == ACLMessage.CANCEL) {
            if(message.getContent().equals(consumer.getLocalName())) {
                System.out.println(consumer.getAID().getLocalName() + " is suspended by " + message.getSender().getLocalName());
                consumer.suspended = true;
            } else if(consumer.getProviders().values().contains(message.getContent())) {
                System.out.println(consumer.getAID().getLocalName() + ": one of my providers got suspended, message received from: " + message.getSender().getLocalName());
                consumer.done = true;
            }
        } else if(message.getPerformative() == ACLMessage.SUBSCRIBE) {
            if(!message.getContent().equals(consumer.getLocalName()) && GraphicHelper.getProducers().contains(message.getContent())) {
                consumer.suspended = true;
            }
        } else
            System.out.println(consumer.getAID().getLocalName() + " received energy from " + message.getSender().getLocalName() + ": " + message.getContent());
    }

    public ReceiveEnergyState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean done() {
        return consumer.suspended || consumer.done;
    }
}
