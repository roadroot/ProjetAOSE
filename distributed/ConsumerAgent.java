import jade.core.Agent;

public class ConsumerAgent extends Agent {
    protected void setup()
    {
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
}
