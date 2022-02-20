package distributed.producer;

import java.util.ArrayList;

import jade.core.Agent;
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

        addBehaviour(new InitializationState(this));
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getProduction() {
        return production;
    }
}
