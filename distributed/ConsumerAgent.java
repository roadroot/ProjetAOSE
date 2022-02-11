import java.util.ArrayList;

public class ConsumerAgent extends ParentAgent {
    private ArrayList<Energy> consumption = new ArrayList<>();
    protected void setup()
    {
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
}
