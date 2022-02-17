package distributed.consumer;

import java.util.ArrayList;
import java.util.HashMap;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import main.Energy;
import main.Position;

public class ConsumerAgent extends Agent {
    private HashMap<Integer, String> providers = new HashMap<>();
    private ArrayList<Energy> consumption;
    private Position position;
    public Position getPosition() {
        return position;
    }
    @SuppressWarnings("unchecked")
    protected void setup()
    {
        Object[] args = getArguments();
        position = (Position) args[0];
        consumption = (ArrayList<Energy>) args[1];
        for(int i=0; i<consumption.size(); i++)
            providers.put(i, null);

        FSMBehaviour behaviour = new FSMBehaviour(this);
        addBehaviour(behaviour);
        behaviour.registerFirstState(new InitializationState(this), InitializationState.NAME);
        behaviour.registerState(new RequestPriceTable(this), RequestPriceTable.NAME);
        behaviour.registerState(new GetPriceTableState(this), GetPriceTableState.NAME);
        behaviour.registerLastState(new FinalState(), FinalState.NAME);
        behaviour.registerTransition(InitializationState.NAME, RequestPriceTable.NAME, InitializationState.GET_PRICE_TABLE);
        behaviour.registerDefaultTransition(RequestPriceTable.NAME, GetPriceTableState.NAME);
        behaviour.registerDefaultTransition(GetPriceTableState.NAME, InitializationState.NAME);
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
    public HashMap<Integer, String> getProviders() {
        return providers;
    }
}
