package tetrisgame.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {
    private static ImageLoader mUniqueInstance = null;

    private HashMap<String, BufferedImage> mImageHash;

    private ImageLoader() {
        mImageHash = new HashMap<String, BufferedImage>();

        char resDirCh[] = (System.getProperty("user.dir") + "\\src\\res\\").toCharArray();
        int size = resDirCh.length;
        for (int i = 0; i < size; i++) {
            if (resDirCh[i] == '\\') {
                resDirCh[i] = '/';
            }
        }
        String resDir = new String(resDirCh);
        File resFile = new File(resDir);

        String filesInRes[] = resFile.list();
        int resDirSize = filesInRes.length;
        for (int i = 0; i < resDirSize; i++) {
            String currDir = resDir + filesInRes[i] + "/";
            String paths[] = new File(currDir).list();
            int pathLen = paths.length;
            for (int j = 0; j < pathLen; j++) {
                String currPath = "../../res/" + filesInRes[i] + "/" + paths[j];
                loadImage(currPath);
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

    private void loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            // TODO: Throw runtime excep or exit program
            assert (false) : "File open failed";
        }
        char pathCh[] = path.toCharArray();

        int startIndex = 0;
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
        char nameCh[] = new char[nameLen];
        for (int i = 0; i < nameLen; i++) {
            nameCh[i] = pathCh[startIndex + i];
        }

        mImageHash.put(new String(nameCh), image);
    }
}