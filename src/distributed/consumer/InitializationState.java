package distributed.consumer;

import jade.core.behaviours.OneShotBehaviour;

public class InitializationState extends OneShotBehaviour {
    private int decision = 0;
    private ConsumerAgent consumer;
    @Override
    public void action() {
        this.consumer.doWait();
    }

    public InitializationState(ConsumerAgent consumer) {
        this.consumer = consumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
