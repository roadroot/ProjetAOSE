package distributed.broker;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

public class BrokerAgent extends Agent {
    protected void setup()
    {
        FSMBehaviour behaviour = new FSMBehaviour(this);
        behaviour.registerFirstState(new InitializationState(this), InitializationState.NAME);
        behaviour.registerLastState(new FinalState(), FinalState.NAME);
        behaviour.registerState(new GetPriceTablesState(this), GetPriceTablesState.NAME);
        behaviour.registerDefaultTransition(InitializationState.NAME, InitializationState.NAME);
        behaviour.registerTransition(InitializationState.NAME, GetPriceTablesState.NAME, InitializationState.GET_TABLES);
        behaviour.registerDefaultTransition(GetPriceTablesState.NAME, InitializationState.NAME);
        addBehaviour(behaviour);
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
}
