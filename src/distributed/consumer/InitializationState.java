package distributed.consumer;

import jade.core.behaviours.OneShotBehaviour;

public class InitializationState extends OneShotBehaviour {
    public static final String NAME = "init";
    public static final int NONE = 0;
    public static final int GET_PRICE_TABLE = 1;
    private int decision = NONE;
    private ConsumerAgent consumer;
    @Override
    public void action() {
        boolean satisfied = true;
        for(String agent :consumer.getProviders().values())
            if(agent == null) {
                satisfied = false;
                break;
            }
        if(satisfied) {
            consumer.doWait();
            System.out.println(consumer.receive().getContent());
        } else
            decision = GET_PRICE_TABLE;
    }

    public InitializationState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
