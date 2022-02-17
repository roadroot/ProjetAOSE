import java.util.ArrayList;

import jade.core.Agent;

public class ProsumerAgent extends Agent{
    private ArrayList<Energy> production = new ArrayList<>();
    private ArrayList<Energy> consumption = new ArrayList<>();
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
        production = (ArrayList<Energy>) args[2];
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
    public ArrayList<Energy> getProduction() {
        return production;
    }
}
