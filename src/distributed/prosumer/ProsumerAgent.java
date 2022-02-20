package distributed.prosumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import distributed.StringConstants;
import main.Position;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import main.Energy;
import main.GraphicHelper;

public class ProsumerAgent extends Agent{
    private HashMap<Integer, String> providers = new HashMap<>();
    private HashMap<Integer, Energy> offers = new HashMap<>();
    private ArrayList<Energy> production = new ArrayList<>();
    private ArrayList<Energy> consumption = new ArrayList<>();
    private Position position;
    public HashMap<Integer, Energy> getOffers() {
        return offers;
    }
    public HashMap<Integer, String> getProviders() {
        return providers;
    }
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
        // FSMBehaviour behaviour = new FSMBehaviour(this);
        ParallelBehaviour pb = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
        //addBehaviour(pb);
        // add producer behaviour
        pb.addSubBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                doWait();
                MessageTemplate mt = MessageTemplate.or(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchContent(StringConstants.GET_PRICE_TABLE)), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
                ACLMessage message = receive(mt);
                if(message.getPerformative() == ACLMessage.REQUEST && message.getContent().equals(StringConstants.GET_PRICE_TABLE)) {
                    System.out.println(getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContent());
                    ArrayList<Energy> energies= getProduction();
                    ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                    try {
                        reply.setContentObject(energies);
                        reply.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
                        Thread.sleep((long)(Math.random() * 1000));
                        send(reply);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(message.getPerformative() == ACLMessage.PROPOSE) {
                    try {
                        System.out.println(getAID().getLocalName() + " " + message.getSender().getLocalName() + " " + message.getContentObject());
                        Energy energy = (Energy) message.getContentObject();
                        boolean valid = false;
                        for(Energy prod:getProduction()) {
                            if(prod.getType() == energy.getType() && prod.price == energy.price) {
                                valid = true;
                                break;
                            }
                        }
                        if(valid) {
                            ACLMessage reply = message.createReply();
                            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                            reply.setContent(energy.toString());
                            send(reply);
                        }
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // add consumer behaviour
        SequentialBehaviour consumerBehaviour = new SequentialBehaviour(this);
        consumerBehaviour.addSubBehaviour(new RequestPriceTableState(this));
        consumerBehaviour.addSubBehaviour(new GetPriceTableState(this));
        consumerBehaviour.addSubBehaviour(new RequestEnergyState(this));
        consumerBehaviour.addSubBehaviour(new ReceiveEnergyState(this));
        pb.addSubBehaviour(consumerBehaviour);

        // FSMBehaviour fconsBehaviour = new FSMBehaviour();
        // fconsBehaviour.registerFirstState(new RequestPriceTableState(this), RequestPriceTableState.NAME);
        // fconsBehaviour.registerState(new GetPriceTableState(this), GetPriceTableState.NAME);
        // fconsBehaviour.registerState(new RequestEnergyState(this), RequestEnergyState.NAME);
        // fconsBehaviour.registerState(new ReceiveEnergyState(this), ReceiveEnergyState.NAME);
        // fconsBehaviour.registerDefaultTransition(RequestPriceTableState.NAME, GetPriceTableState.NAME);
        // fconsBehaviour.registerDefaultTransition(GetPriceTableState.NAME, RequestEnergyState.NAME);
        // fconsBehaviour.registerDefaultTransition(RequestEnergyState.NAME, ReceiveEnergyState.NAME);
        // pb.addSubBehaviour(fconsBehaviour);
        addBehaviour(pb);
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
    public ArrayList<Energy> getProduction() {
        return production;
    }
}
