import java.nio.file.Files;
import java.nio.file.Path;
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
    private int width;
    private int height;
    private int titleWidth;
    private int titleHeight;
    public int getSquareWidth() {
        return width * titleWidth;
    }
    public int getSquareHeight() {
        return height * titleHeight;
    }
    public int getWidth() {
        return width;
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
        } catch(ClassCastException e) {
            throw new Exception("Configuration does not contains all required fields in \"" + path + "\" please visit " + GITHUB + " for more information");
        } catch(NullPointerException e) {
            throw new Exception("Configuration does not contains all required fields in \"" + path + "\" please visit " + GITHUB + " for more information");
        }
    }
}