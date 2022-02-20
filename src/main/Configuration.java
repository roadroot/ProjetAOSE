package main;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import distributed.broker.BrokerAgent;
import distributed.consumer.ConsumerAgent;
import distributed.producer.ProducerAgent;
import distributed.prosumer.ProsumerAgent;
import flexjson.JSONDeserializer;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Configuration {
    private static final String GITHUB = "https://github.com/roadroot/ProjetAOSE";
    private static final String WORLD = "world";
    private static final String AGENTS = "agents";
    private static final String ENERGY_TYPE = "type";
    private static final String ENERGY_AMOUNT = "amount";
    private static final String ENERGY_PRICE = "price";
    private static final String ENERGY_DURATION = "duration";
    private static final String WORLD_WIDTH = "width";
    private static final String WORLD_HEIGHT = "height";
    private static final String WORLD_TILE_WIDTH = "tile width";
    private static final String WORLD_TILE_HEIGHT = "tile height";
    private static final String AGENT_CONSUMPTION = "consumption";
    private static final String AGENT_NAME = "name";
    private static final String AGENT_TYPE = "type";
    private static final String AGENT_PRODUCTION = "production";
    private static final String AGENT_POSITION_X = "positionX";
    private static final String AGENT_POSITION_Y = "positionY";
    private int width;
    private int height;
    private int titleWidth;
    private int titleHeight;
    private Map<String, AgentController> agents;
    public int getSquareWidth() {
        return width * titleWidth;
    }
    public int getSquareHeight() {
        return height * titleHeight;
    }
    public int getWidth() {
        return width;
    }
    public Map<String, AgentController> getAgents() {
        return agents;
    }
    public int getHeight() {
        return height;
    }
    public int getTileWidth() {
        return titleWidth;
    }
    public int getTileHeight() {
        return titleHeight;
    }
    @SuppressWarnings("unchecked")
    public Configuration(String path, AgentContainer mc) throws Exception {
        GraphicHelper.configuration = this;
        int brokers = 0;
        try {
            Map<String, Object> json = new JSONDeserializer<Map<String, Object>>().deserialize(Files.readAllLines(Paths.get(path)).stream().reduce("", (l1, l2)->l1+"\n"+l2));
            json.get(WORLD);
            width = (int) ((Map<String, Object>)json.get(WORLD)).get(WORLD_WIDTH);
            height = (int) ((Map<String, Object>)json.get(WORLD)).get(WORLD_HEIGHT);
            titleWidth = (int) ((Map<String, Object>)json.get(WORLD)).get(WORLD_TILE_WIDTH);
            titleHeight = (int) ((Map<String, Object>)json.get(WORLD)).get(WORLD_TILE_HEIGHT);

            agents = new HashMap<>();
            ArrayList<Map<String, Object>> agentJson = (ArrayList<Map<String, Object>>) json.get(AGENTS);
            for(Map<String, Object> agentJ : agentJson) {
                String agentName = (String) agentJ.get(AGENT_NAME);
                int agentType = (int) agentJ.get(AGENT_TYPE);
                GraphicHelper.agentTypes.put(agentName, agentType);
                if(agentType == 0) {
                    AgentController ac = mc.createNewAgent(agentName, BrokerAgent.class.getName(), null);
                    agents.put(agentName, ac);
                    brokers ++;
                }
                else if((int) agentJ.get(AGENT_TYPE) == 1) {
                    Position p = new Position((int) agentJ.get(AGENT_POSITION_X), (int) agentJ.get(AGENT_POSITION_Y));
                    GraphicHelper.positions.put(agentName, p);
                    Object args[] = new Object[2];
                    args[0] = p;
                    ArrayList<Energy> energies = new ArrayList<>();
                    args[1] = energies;
                    for(Map<String, Object> energy : (ArrayList<Map<String, Object>>) agentJ.get(AGENT_CONSUMPTION))
                        energies.add(new Energy((int) energy.get(ENERGY_AMOUNT), EnergyType.get((String) energy.get(ENERGY_TYPE)), (int) energy.get(ENERGY_DURATION)));
                    AgentController ac = mc.createNewAgent(agentName, ConsumerAgent.class.getName(), args);
                    agents.put(agentName, ac);
                } else if((int) agentJ.get(AGENT_TYPE) == 2) {
                    Position p = new Position((int) agentJ.get(AGENT_POSITION_X), (int) agentJ.get(AGENT_POSITION_Y));
                    GraphicHelper.positions.put(agentName, p);
                    Object args[] = new Object[2];
                    args[1] = p;
                    ArrayList<Energy> energies = new ArrayList<>();
                    args[1] = energies;
                    for(Map<String, Object> energy : (ArrayList<Map<String, Object>>) agentJ.get(AGENT_PRODUCTION))
                        energies.add(new Energy(EnergyType.get((String) energy.get(ENERGY_TYPE)), (int) energy.get(ENERGY_PRICE)));
                    AgentController ac = mc.createNewAgent(agentName, ProducerAgent.class.getName(), args);
                    agents.put(agentName, ac);
                }
                else if((int) agentJ.get(AGENT_TYPE) == 3) {
                    Position p = new Position((int) agentJ.get(AGENT_POSITION_X), (int) agentJ.get(AGENT_POSITION_Y));
                    GraphicHelper.positions.put(agentName, p);
                    Object args[] = new Object[3];
                    args[0] = p;
                    ArrayList<Energy> energies = new ArrayList<>();
                    args[1] = energies;
                    ArrayList<Energy> pEnergies = new ArrayList<>();
                    args[2] = pEnergies;
                    for(Map<String, Object> energy : (ArrayList<Map<String, Object>>) agentJ.get(AGENT_CONSUMPTION))
                        energies.add(new Energy((int) energy.get(ENERGY_AMOUNT), EnergyType.get((String) energy.get(ENERGY_TYPE)), (int) energy.get(ENERGY_DURATION)));
                    for(Map<String, Object> energy : (ArrayList<Map<String, Object>>) agentJ.get(AGENT_PRODUCTION))
                        pEnergies.add(new Energy(EnergyType.get((String) energy.get(ENERGY_TYPE)), (int) energy.get(ENERGY_PRICE)));
                    AgentController ac = mc.createNewAgent(agentName, ProsumerAgent.class.getName(), args);
                    agents.put(agentName, ac);
                }
            }
            if(brokers != 1)
                throw new Exception("Configuration contains not not one broker agent in \"" + path + "\" please visit " + GITHUB + " for more information");
        } catch(ClassCastException e) {
            throw new Exception("Configuration does not contain all required fields in \"" + path + "\" please visit " + GITHUB + " for more information");
        } catch(NullPointerException e) {
            throw new Exception("Configuration does not contain all required fields in \"" + path + "\" please visit " + GITHUB + " for more information");
        }
    }
}