package distributed.broker;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.UnreadableException;

public class InitializationState extends OneShotBehaviour {
    public static final String NAME = "init";
    private int decision = 0;
    private BrokerAgent broker;
    @Override
    public void action() {
        broker.doWait();
        try {
            System.out.println(broker.receive().getContentObject());
        } catch (UnreadableException e) {
            e.printStackTrace();
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
