package tetrisgame.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {
    private static ImageLoader mUniqueInstance = null;

    private HashMap<String, BufferedImage> mImageHash;
    private final String PATH = System.getProperty("user.dir") + "/images/";

    private ImageLoader() {
        mImageHash = new HashMap<String, BufferedImage>();

        File resource = new File(PATH);

        String[] resourceList = resource.list();
        for (int i = 0; i < resourceList.length; i++) {
            String imageFolder = PATH + resourceList[i] + "/";
            String[] imageFileList = new File(imageFolder).list();
            for (int j = 0; j < imageFileList.length; j++) {
                String imageFilePath = imageFolder + imageFileList[j];
                loadImage(imageFilePath);
            }
        }
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

    private void loadImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            // TODO: Throw runtime excep or exit program
            assert (false) : "File open failed";
        }
        char[] pathCh = imagePath.toCharArray();

        int startIndex;
        int dotIndex = 0;
        for (startIndex = pathCh.length - 1; dotIndex >= 0; startIndex--) {
            if (pathCh[startIndex - 1] == '/') {
                break;
            }
            if (pathCh[startIndex] == '.') {
                dotIndex = startIndex;
            }
        }
        int nameLen = dotIndex - startIndex;
        char[] nameCh = new char[nameLen];
        for (int i = 0; i < nameLen; i++) {
            nameCh[i] = pathCh[startIndex + i];
        }
        mImageHash.put(new String(nameCh), image);
    }
}
