package tetrisgame.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {
    private static ImageLoader mUniqueInstance = null;

    private HashMap<String, BufferedImage> mImageHash;

    private ImageLoader() {
        mImageHash = new HashMap<String, BufferedImage>();
        // Default tiles
        loadImage("tile", "tile_white");
        loadImage("tile", "tile_blue");
        loadImage("tile", "tile_frame");
        loadImage("tile", "tile_lightgreen");
        loadImage("tile", "tile_orange");
        loadImage("tile", "tile_purple");
        loadImage("tile", "tile_red");
        loadImage("tile", "tile_skyblue");
        loadImage("tile", "tile_white");
        loadImage("tile", "tile_yellow");
        // Item tiles
        loadImage("tile", "item_tile_lineeraser");
        loadImage("tile", "item_tile_weight");
        loadImage("tile", "item_tile_slowing");
        loadImage("tile", "item_tile_removingall");
        // Anim tiles
        loadImage("tile", "tile_remove1");
        loadImage("tile", "tile_remove2");
        loadImage("tile", "tile_lineeraser1");
        loadImage("tile", "tile_lineeraser2");
        // Background images
        loadImage("background", "background_board");
        loadImage("background", "background_nextboard");
        loadImage("background", "background_panel");
    }

    public static ImageLoader getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new ImageLoader();
        }
        return mUniqueInstance;
    }

    public BufferedImage getTexture(String name) {
        return mImageHash.get(name);
    }

    private void loadImage(String file, String name) {
        String path = String.format("../../res/%s/%s.png", file, name);
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            // TODO: Throw runtime excep or exit program
            assert (false) : "File open failed";
        }
        mImageHash.put(name, image);
    }
}
