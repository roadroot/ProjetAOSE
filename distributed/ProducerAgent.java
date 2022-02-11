import java.util.ArrayList;

public class ProducerAgent extends ParentAgent {
    private ArrayList<Energy> production = new ArrayList<>();
    protected void setup()
    {
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getProduction() {
        return production;
    }
}
