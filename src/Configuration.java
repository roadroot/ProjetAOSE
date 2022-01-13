import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import flexjson.JSONDeserializer;

public class Configuration {
    private static final String GITHUB = "https://github.com/roadroot/ProjetAOSE";
    private static final String WORLD = "world";
    private static final String AGENTS = "agents";
    private static final String WORLD_WIDTH = "width";
    private static final String WORLD_HEIGHT = "height";
    private static final String WORLD_TILE_WIDTH = "tile width";
    private static final String WORLD_TILE_HEIGHT = "tile height";
    private static final String AGENT_CONSUMPTION = "consumption";
    private static final String AGENT_PRODUCTION = "production";
    private static final String AGENT_ENERGY_TYPE = "energy type";
    private static final String AGENT_POSITION_X = "positionX";
    private static final String AGENT_POSITION_Y = "positionY";
    private int width;
    private int height;
    private int titleWidth;
    private int titleHeight;
    private ArrayList<Agent> agents;
    public int getSquareWidth() {
        return width * titleWidth;
    }
    public int getSquareHeight() {
        return height * titleHeight;
    }
    public int getWidth() {
        return width;
    }
    public ArrayList<Agent> getAgents() {
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
    public Configuration(String path) throws Exception {
        try {
            Map<String, Object> json = new JSONDeserializer<Map<String, Object>>().deserialize(Files.readString(Path.of(path)));
            json.get(WORLD);
            width = (int) ((Map<String, Object>)json.get(WORLD)).get(WORLD_WIDTH);
            height = (int) ((Map<String, Object>)json.get(WORLD)).get(WORLD_HEIGHT);
            titleWidth = (int) ((Map<String, Object>)json.get(WORLD)).get(WORLD_TILE_WIDTH);
            titleHeight = (int) ((Map<String, Object>)json.get(WORLD)).get(WORLD_TILE_HEIGHT);
            agents = new ArrayList<>();
            var agentJson = (ArrayList<Map<String, Object>>) json.get(AGENTS);
            for(var agentJ : agentJson) {
                agents.add(new Agent((int) agentJ.get(AGENT_CONSUMPTION), (int) agentJ.get(AGENT_PRODUCTION), EnergyType.get((String) agentJ.get(AGENT_ENERGY_TYPE)), (int) agentJ.get(AGENT_POSITION_X), (int) agentJ.get(AGENT_POSITION_Y)));
            }
        } catch(ClassCastException e) {
            throw new Exception("Configuration does not contains all required fields in \"" + path + "\" please visit " + GITHUB + " for more information");
        } catch(NullPointerException e) {
            throw new Exception("Configuration does not contains all required fields in \"" + path + "\" please visit " + GITHUB + " for more information");
        }
    }
}