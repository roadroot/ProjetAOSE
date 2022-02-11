import java.util.ArrayList;

public class ProsumerAgent extends ParentAgent{
    private ArrayList<Energy> production = new ArrayList<>();
    private ArrayList<Energy> consumption = new ArrayList<>();
    protected void setup()
    {
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
    public ArrayList<Energy> getProduction() {
        return production;
    }
}
