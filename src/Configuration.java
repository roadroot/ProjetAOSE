import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import flexjson.JSONDeserializer;
import jade.core.Agent;
import jade.wrapper.AgentContainer;

public class Configuration {
    private static final String GITHUB = "https://github.com/roadroot/ProjetAOSE";
    private static final String WORLD = "world";
    private static final String AGENTS = "agents";
    private static final String ENERGY_TYPE = "type";
    private static final String ENERGY_AMOUNT = "amount";
    private static final String ENERGY_PRICE = "price";
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
    private Map<String, Agent> agents;
    public int getSquareWidth() {
        return width * titleWidth;
    }
    public int getSquareHeight() {
        return height * titleHeight;
    }
    public int getWidth() {
        return width;
    }
    public Map<String, Agent> getAgents() {
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
                if((int) agentJ.get(AGENT_TYPE) == 0) {
                    agents.put((String) agentJ.get(AGENT_NAME), new BrokerAgent());
                }
                else if((int) agentJ.get(AGENT_TYPE) == 1) {
                    ConsumerAgent ag = new ConsumerAgent();
                    ag.setPositionX((int) agentJ.get(AGENT_POSITION_X));
                    ag.setPositionY((int) agentJ.get(AGENT_POSITION_Y));
                    for(Map<String, Object> energy : (ArrayList<Map<String, Object>>) agentJ.get(AGENT_CONSUMPTION))
                        ag.getConsumption().add(new Energy((int) energy.get(ENERGY_AMOUNT), EnergyType.get((String) energy.get(ENERGY_TYPE))));
                    agents.put((String) agentJ.get(AGENT_NAME), ag);
                } else if((int) agentJ.get(AGENT_TYPE) == 2) {
                    ProducerAgent ag = new ProducerAgent();
                    ag.setPositionX((int) agentJ.get(AGENT_POSITION_X));
                    ag.setPositionY((int) agentJ.get(AGENT_POSITION_Y));
                    for(Map<String, Object> energy : (ArrayList<Map<String, Object>>) agentJ.get(AGENT_PRODUCTION))
                        ag.getProduction().add(new Energy((int) energy.get(ENERGY_AMOUNT), EnergyType.get((String) energy.get(ENERGY_TYPE)), (int) energy.get(ENERGY_PRICE)));
                    agents.put((String) agentJ.get(AGENT_NAME), ag);
                }
                else if((int) agentJ.get(AGENT_TYPE) == 3) {
                    ProsumerAgent ag = new ProsumerAgent();
                    ag.setPositionX((int) agentJ.get(AGENT_POSITION_X));
                    ag.setPositionY((int) agentJ.get(AGENT_POSITION_Y));
                    for(Map<String, Object> energy : (ArrayList<Map<String, Object>>) agentJ.get(AGENT_PRODUCTION))
                        ag.getProduction().add(new Energy((int) energy.get(ENERGY_AMOUNT), EnergyType.get((String) energy.get(ENERGY_TYPE)), (int) energy.get(ENERGY_PRICE)));
                    for(Map<String, Object> energy : (ArrayList<Map<String, Object>>) agentJ.get(AGENT_CONSUMPTION))
                        ag.getConsumption().add(new Energy((int) energy.get(ENERGY_AMOUNT), EnergyType.get((String) energy.get(ENERGY_TYPE))));
                    agents.put((String) agentJ.get(AGENT_NAME), ag);
                }
            }
        } catch(ClassCastException e) {
            throw new Exception("Configuration does not contains all required fields in \"" + path + "\" please visit " + GITHUB + " for more information");
        } catch(NullPointerException e) {
            throw new Exception("Configuration does not contains all required fields in \"" + path + "\" please visit " + GITHUB + " for more information");
        }
    }
}