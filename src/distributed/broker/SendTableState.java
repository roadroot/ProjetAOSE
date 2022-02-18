package distributed.broker;

import java.io.IOException;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendTableState extends OneShotBehaviour {
    public static final String NAME = "send_table_state";
    public static final int NONE = 0;
    private int decision = NONE;
    private BrokerAgent broker;
    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(broker.tableRequest);
        try {
            message.setContentObject(broker.table);
            broker.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SendTableState(BrokerAgent broker) {
        this.broker = broker;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
