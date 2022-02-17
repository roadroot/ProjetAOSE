package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class GraphicHelper {
    public static Map<String, Position> positions = new HashMap<>();
    public static Map<String, Integer> agentTypes = new HashMap<>();
    public static Map<String, Boolean> agentStates = new HashMap<>();
    public static Configuration configuration;
    public static String getBroker() {
        for(Map.Entry<String, Integer> entry : agentTypes.entrySet())
            if(entry.getValue() == 0) return entry.getKey();
        return null;
    }

    public static ArrayList<String> getConsumers() {
        ArrayList<String> consumers = new ArrayList<>();
        agentTypes.forEach((k, v) -> {if(v == 1) consumers.add(k);});
        return consumers;
    }

    public static ArrayList<String> getProducers() {
        ArrayList<String> producers = new ArrayList<>();
        agentTypes.forEach((k, v) -> {if(v == 2) producers.add(k);});
        return producers;
    }

    public static ArrayList<String> getProsumers() {
        ArrayList<String> prosumers = new ArrayList<>();
        agentTypes.forEach((k, v) -> {if(v == 3) prosumers.add(k);});
        return prosumers;
    }

}
