package distributed.broker;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

public class BrokerAgent extends Agent {
    protected void setup()
    {
        FSMBehaviour behaviour = new FSMBehaviour(this);
        behaviour.registerFirstState(new InitializationState(this), InitializationState.NAME);
        behaviour.registerLastState(new InitializationState(this), InitializationState.NAME);
        addBehaviour(behaviour);
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
}
