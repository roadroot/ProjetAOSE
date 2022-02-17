package distributed.consumer;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import main.Energy;
import main.Position;

public class ConsumerAgent extends Agent {
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

        FSMBehaviour behaviour = new FSMBehaviour(this);
        addBehaviour(behaviour);
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
}
