import jade.core.Agent;

public class Procumer extends Agent{
    protected void setup()
    {
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
}
