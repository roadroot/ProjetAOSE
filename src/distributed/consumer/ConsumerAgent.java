package distributed.consumer;

import java.util.ArrayList;
import java.util.HashMap;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import main.Energy;
import main.Position;

public class ConsumerAgent extends Agent {
    private HashMap<Integer, String> providers = new HashMap<>();
    private HashMap<Integer, Energy> offers = new HashMap<>();
    private ArrayList<Energy> consumption;
    private Position position;
    protected boolean suspended;
    protected boolean done;

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
        FSMBehaviour cyclicBehaviour = new FSMBehaviour(this);
        cyclicBehaviour.registerFirstState(new RequestPriceTableState(this), RequestPriceTableState.NAME);
        cyclicBehaviour.registerFirstState(new GetPriceTableState(this), GetPriceTableState.NAME);
        cyclicBehaviour.registerFirstState(new RequestEnergyState(this), RequestEnergyState.NAME);
        cyclicBehaviour.registerFirstState(new ReceiveEnergyState(this), ReceiveEnergyState.NAME);
        cyclicBehaviour.registerFirstState(new SuspendedState(this), SuspendedState.NAME);
        cyclicBehaviour.registerDefaultTransition(RequestPriceTableState.NAME, GetPriceTableState.NAME);
        cyclicBehaviour.registerDefaultTransition(GetPriceTableState.NAME, RequestEnergyState.NAME);
        cyclicBehaviour.registerDefaultTransition(RequestEnergyState.NAME, ReceiveEnergyState.NAME);
        cyclicBehaviour.registerDefaultTransition(ReceiveEnergyState.NAME, SuspendedState.NAME);
        cyclicBehaviour.registerDefaultTransition(SuspendedState.NAME, RequestPriceTableState.NAME);
        addBehaviour(cyclicBehaviour);
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
    public HashMap<Integer, String> getProviders() {
        return providers;
    }
}
