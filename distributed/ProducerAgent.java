import jade.core.Agent;

public class ProducerAgent extends Agent {
    protected void setup()
    {
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }

}
