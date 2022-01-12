import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import flexjson.JSONDeserializer;

public class Configuration {
    private static final String WORLD = "world";
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
    public Configuration(String path) throws IOException {
        Map<String, Map<String, Object>> json = new JSONDeserializer<Map<String, Map<String, Object>>>().deserialize(Files.readString(Path.of(path)));
        json.get(WORLD);
        width = (int) json.get(WORLD).get(WORLD_WIDTH);
        height = (int) json.get(WORLD).get(WORLD_HEIGHT);
        titleWidth = (int) json.get(WORLD).get(WORLD_TILE_WIDTH);
        titleHeight = (int) json.get(WORLD).get(WORLD_TILE_HEIGHT);
    }
}