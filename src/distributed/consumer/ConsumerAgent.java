package distributed.consumer;

import java.util.ArrayList;
import java.util.HashMap;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import main.Energy;
import main.Position;

public class ConsumerAgent extends Agent {
    private HashMap<Integer, String> providers = new HashMap<>();
    private HashMap<Integer, Energy> offers = new HashMap<>();
    private ArrayList<Energy> consumption;
    private Position position;
    protected HashMap<String, ArrayList<Energy>> table = null;
    public Position getPosition() {
        return position;
    }
    public HashMap<Integer, Energy> getOffers() {
        return offers;
    }
    public void setOffers(HashMap<Integer, Energy> offers) {
        this.offers = offers;
    }
    @SuppressWarnings("unchecked")
    protected void setup()
    {
        Object[] args = getArguments();
        position = (Position) args[0];
        consumption = (ArrayList<Energy>) args[1];
        for(int i=0; i<consumption.size(); i++) {
            providers.put(i, null);
            offers.put(i, null);
        }

        // FSMBehaviour behaviour = new FSMBehaviour(this);
        SequentialBehaviour behaviour = new SequentialBehaviour(this);
        addBehaviour(behaviour);
        // behaviour.registerFirstState(new InitializationState(this), InitializationState.NAME);
        // behaviour.registerState(new SequentialBehaviour(this), ReceiveEnergyState.NAME);
        // behaviour.registerLastState(new FinalState(), FinalState.NAME);
        behaviour.addSubBehaviour(new InitializationState(this));
        SequentialBehaviour subscribeToEnergy = new SequentialBehaviour(this);
        behaviour.addSubBehaviour(subscribeToEnergy);
        subscribeToEnergy.addSubBehaviour(new RequestPriceTableState(this));
        subscribeToEnergy.addSubBehaviour(new GetPriceTableState(this));
        subscribeToEnergy.addSubBehaviour(new RequestEnergyState(this));
        subscribeToEnergy.addSubBehaviour(new ReceiveEnergyState(this));
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
    public HashMap<Integer, String> getProviders() {
        return providers;
    }
}
