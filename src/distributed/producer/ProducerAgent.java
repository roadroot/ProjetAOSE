package distributed.producer;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import main.Energy;
import main.Position;

public class ProducerAgent extends Agent {
    private ArrayList<Energy> production = new ArrayList<>();
    private Position position;
    public Position getPosition() {
        return position;
    }
    @SuppressWarnings("unchecked")
    protected void setup()
    {
        Object[] args = getArguments();
        position = (Position) args[0];
        production = (ArrayList<Energy>) args[1];

        FSMBehaviour behaviour = new FSMBehaviour(this);
        behaviour.registerFirstState(new InitializationState(this), InitializationState.NAME);
        behaviour.registerState(new SendPriceTableState(this), SendPriceTableState.NAME);
        behaviour.registerDefaultTransition(InitializationState.NAME, InitializationState.NAME);
        behaviour.registerTransition(InitializationState.NAME, SendPriceTableState.NAME, InitializationState.DECISION_SEND_TABLE);
        behaviour.registerDefaultTransition(SendPriceTableState.NAME, InitializationState.NAME);
        behaviour.registerLastState(new FinalState(), FinalState.NAME);
        addBehaviour(behaviour);
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getProduction() {
        return production;
    }
}
