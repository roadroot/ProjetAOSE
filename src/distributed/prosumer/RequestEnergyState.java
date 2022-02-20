package distributed.prosumer;

import java.io.IOException;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import main.Energy;

public class RequestEnergyState extends OneShotBehaviour {
    public static final String NAME = "request_energy_state";
    private ProsumerAgent prosumer;
    @Override
    public void action() {
        System.out.println(45545);
        System.out.println(45545);
        System.out.println(45545);
        System.out.println(45545);
        System.out.println(45545);
        for(int i = 0; i<prosumer.getOffers().size(); i++) {
            if(prosumer.getOffers().get(i) == null || prosumer.getProviders().get(i) == null) continue;
            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(prosumer.getProviders().get(i), AID.ISLOCALNAME));
            Energy offer = new Energy(prosumer.getConsumption().get(i).amount, prosumer.getConsumption().get(i).type, prosumer.getOffers().get(i).price);
            try {
                message.setContentObject(offer);
                prosumer.send(message);
            } catch (IOException e) {
                System.out.println("Error received from " + this.getClass().getName());
                e.printStackTrace();
            }
        }
    }

    public RequestEnergyState(ProsumerAgent consumer) {
        this.prosumer = consumer;
    }
}
