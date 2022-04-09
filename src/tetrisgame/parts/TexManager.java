package tetrisgame.parts;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TexManager {
    private static TexManager mUniqueInstance = null;

    private HashMap<String, BufferedImage> mTexHash;
    private static final int SIZE = 9;

    private TexManager() {
        mTexHash = new HashMap<String, BufferedImage>();
        loadTile("tile_blue");
        loadTile("tile_frame");
        loadTile("tile_lightgreen");
        loadTile("tile_orange");
        loadTile("tile_purple");
        loadTile("tile_red");
        loadTile("tile_skyblue");
        loadTile("tile_white");
        loadTile("tile_yellow");
        loadTile("tile_remove1");
        loadTile("tile_remove2");

        loadBackground("background_board");
        loadBackground("background_nextboard");
        loadBackground("background_panel");
    }

    public static TexManager getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new TexManager();
        }
        return mUniqueInstance;
    }

    public BufferedImage getTexture(String name) {
        return mTexHash.get(name);
    }

    private void loadTile(String name) {
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

    private void loadBackground(String name) {
        BufferedImage tex = null;
        StringBuffer str = new StringBuffer();
        str.append("../../res/background/");
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
