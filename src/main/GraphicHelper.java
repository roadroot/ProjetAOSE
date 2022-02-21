package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jade.core.Agent;


public class GraphicHelper extends Agent {
    public static Agent agent;
    public static ArrayList<String> agents = new ArrayList<>();
    public static Map<String, Position> positions = new HashMap<>();
    public static Map<String, Integer> agentTypes = new HashMap<>();
    public static Map<String, Boolean> agentStates = new HashMap<>();
    public static ArrayList<Message> messages = new ArrayList<>();
    public static Configuration configuration;
    public static String getBroker() {
        for(Map.Entry<String, Integer> entry : agentTypes.entrySet())
            if(entry.getValue() == 0) return entry.getKey();
        return null;
    }

    public static ArrayList<String> getConsumers() {
        ArrayList<String> consumers = new ArrayList<>();
        agentTypes.forEach((k, v) -> {if(agents.contains(k) && v == 1) consumers.add(k);});
        return consumers;
    }

    public static ArrayList<String> getProducers() {
        ArrayList<String> producers = new ArrayList<>();
        agentTypes.forEach((k, v) -> {if(agents.contains(k) && v == 2) producers.add(k);});
        return producers;
    }

    public static ArrayList<String> getProsumers() {
        ArrayList<String> prosumers = new ArrayList<>();
        agentTypes.forEach((k, v) -> {if(agents.contains(k) && v == 3) prosumers.add(k);});
        return prosumers;
    }

    public static void suspendAgent(String name) {
        agents.remove(name);
    }

    public static void addAgent(String name) {
        agents.add(name);
    }

    @Override
    protected void setup() {
        agent = this;
    }
}
