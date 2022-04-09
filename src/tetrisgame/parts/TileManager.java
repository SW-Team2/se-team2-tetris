package tetrisgame.parts;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TileManager {
    private static TileManager mUniqueInstance = null;

    private HashMap<String, BufferedImage> mTexHash;
    private static final int SIZE = 9;

    private TileManager() {
        mTexHash = new HashMap<String, BufferedImage>();
        loadTex("tile_blue");
        loadTex("tile_frame");
        loadTex("tile_lightgreen");
        loadTex("tile_orange");
        loadTex("tile_purple");
        loadTex("tile_red");
        loadTex("tile_skyblue");
        loadTex("tile_white");
        loadTex("tile_yellow");
        loadTex("tile_remove");
    }

    public static TileManager getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new TileManager();
        }
        return mUniqueInstance;
    }

    public BufferedImage getTexture(String name) {
        return mTexHash.get(name);
    }

    private void loadTex(String name) {
        BufferedImage tex = null;
        StringBuffer str = new StringBuffer();
        str.append("../../res/tiles/");
        str.append(name);
        str.append(".png");
        try {
            tex = ImageIO.read(getClass().getResourceAsStream(str.toString()));
        } catch (IOException e) {
            assert (false) : "File open failed";
        }
        mTexHash.put(name, tex);
    }
}
