package distributed.broker;

import java.util.ArrayList;
import java.util.HashMap;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import main.Energy;

public class BrokerAgent extends Agent {
    HashMap<String, ArrayList<Energy>> table;
    AID tableRequest;
    protected void setup()
    {
        FSMBehaviour behaviour = new FSMBehaviour(this);
        behaviour.registerFirstState(new InitializationState(this), InitializationState.NAME);
        behaviour.registerLastState(new FinalState(), FinalState.NAME);
        behaviour.registerState(new GetPriceTablesState(this), GetPriceTablesState.NAME);
        behaviour.registerState(new SendTableState(this), SendTableState.NAME);
        behaviour.registerDefaultTransition(InitializationState.NAME, InitializationState.NAME);
        behaviour.registerTransition(InitializationState.NAME, GetPriceTablesState.NAME, InitializationState.GET_TABLES);
        behaviour.registerTransition(GetPriceTablesState.NAME, GetPriceTablesState.NAME, GetPriceTablesState.GET_OTHER);
        behaviour.registerTransition(GetPriceTablesState.NAME, SendTableState.NAME, GetPriceTablesState.SEND_TABLE);
        behaviour.registerDefaultTransition(SendTableState.NAME, InitializationState.NAME);
        addBehaviour(behaviour);
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
}
