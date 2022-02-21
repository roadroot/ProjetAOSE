package distributed.prosumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import distributed.StringConstants;
import main.Position;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import main.Energy;
import main.GraphicHelper;
import main.Message;

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
        // ParallelBehaviour pb = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
        SequentialBehaviour behaviour = new SequentialBehaviour();
        addBehaviour(behaviour);
        // add consumer behaviour
        // SequentialBehaviour consumerBehaviour = new SequentialBehaviour(this);
        // consumerBehaviour.addSubBehaviour(new RequestPriceTableState(this));
        // consumerBehaviour.addSubBehaviour(new GetPriceTableState(this));
        // consumerBehaviour.addSubBehaviour(new RequestEnergyState(this));
        // consumerBehaviour.addSubBehaviour(new ReceiveEnergyState(this));
        // pb.addSubBehaviour(consumerBehaviour);
        //addBehaviour(pb);
        // add producer behaviour
        behaviour.addSubBehaviour(new RequestPriceTableState(this));
        behaviour.addSubBehaviour(new CyclicBehaviour(this) {
            boolean done = false;
            @Override
            public void action() {
                ACLMessage message = blockingReceive();
                GraphicHelper.messages.add(new Message(message));
                if(message.getPerformative() == ACLMessage.CANCEL) {
                    if(message.getContent().equals(getLocalName())) {
                        System.out.println(getLocalName() + " is suspended by " + message.getSender().getLocalName());
                        done = true;
                    } else if(getProviders().values().contains(message.getContent())) {
                        System.out.println(getLocalName() + ": one of my providers got suspended, message received from: " + message.getSender().getLocalName());
                        done = true;
                    }
                } else if(message.getPerformative() == ACLMessage.SUBSCRIBE) {
                    if(done && message.getContent().equals(getLocalName())) {
                        System.out.println(getLocalName() + " is unsuspended by " + message.getSender().getLocalName());
                        done = false;
                        ACLMessage reply = new ACLMessage(ACLMessage.REQUEST);
                        reply.setContent(StringConstants.GET_PRICE_TABLE);
                        reply.addReceiver(new AID(GraphicHelper.getBroker(), AID.ISLOCALNAME));
                        send(reply);
                    }
                    if(!message.getContent().equals(getLocalName()) && GraphicHelper.getProducers().contains(message.getContent())) {
                        done = true;
                    }
                } else
                if(!done && message.getPerformative() == ACLMessage.REQUEST && message.getContent().equals(StringConstants.GET_PRICE_TABLE)) {
                    System.out.println(getLocalName() + " received from " + message.getSender().getLocalName() + ": " + message.getContent());
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
                } else if(!done && message.getPerformative() == ACLMessage.PROPOSE) {
                    try {
                        System.out.println(getLocalName() + " received proposal from " + message.getSender().getLocalName() + ": " + message.getContentObject());
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
                } else if(!done && message.getPerformative() == ACLMessage.INFORM) {
                    try {
                        System.out.println(getLocalName() + ": " + message.getSender().getLocalName() + " requested energy: " + message.getContentObject());
                        HashMap<String, ArrayList<Energy>> table = (HashMap<String, ArrayList<Energy>>) message.getContentObject();
                        getProviders().clear();
                        getOffers().clear();
                        for(int i =0; i<getConsumption().size(); i++) {
                            String bestOfferer = "";
                            Energy bestOffer = null;
                            for(String offerer: table.keySet())
                                for(Energy offer : table.get(offerer))
                                    if(offer.type == getConsumption().get(i).type && (bestOffer == null||offer.price<bestOffer.price)) {
                                        bestOfferer = offerer;
                                        bestOffer = offer;
                                    }
                            if(bestOffer != null) {
                                getProviders().put(i, bestOfferer);
                                getOffers().put(i, bestOffer);
                            }
                        }
                        for(int i = 0; i<getOffers().size(); i++) {
                            if(getOffers().get(i) == null || getProviders().get(i) == null) continue;
                            ACLMessage reply = new ACLMessage(ACLMessage.PROPOSE);
                            reply.addReceiver(new AID(getProviders().get(i), AID.ISLOCALNAME));
                            Energy offer = new Energy(getConsumption().get(i).amount, getConsumption().get(i).type, getOffers().get(i).price, getConsumption().get(i).duration);
                            reply.setContentObject(offer);
                            send(reply);
                        }
                    } catch (UnreadableException|IOException e) {
                        System.out.println("Error received from " + this.getClass().getName());
                        e.printStackTrace();
                    }
                } else
                    System.out.println(getLocalName() + ": received energy from " + message.getSender().getLocalName() + " :" + message.getContent());

            }
        });
        System.out.println(this.getClass().getName() + " " + getLocalName() + " set up");
    }
    public ArrayList<Energy> getConsumption() {
        return consumption;
    }
    public ArrayList<Energy> getProduction() {
        return production;
    }
}
