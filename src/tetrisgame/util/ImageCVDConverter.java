package tetrisgame.util;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class ImageCVDConverter {
    private static int A_MASK = 0xff000000;
    private static int R_MASK = 0x00ff0000;
    private static int G_MASK = 0x0000ff00;
    private static int B_MASK = 0x000000ff;

    public static BufferedImage convert(BufferedImage imgBuf, int CVDMode) {
        int w = imgBuf.getWidth();
        int h = imgBuf.getHeight();
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(imgBuf, 0, 0, null);
        int[] rgb = img.getRGB(0, 0, w, h, null, 0, w);
        for (int i = 0; i < rgb.length; i++) {
            switch (CVDMode) {
                case 0:

                    break;
                case 1:
                    rgb[i] = ReColorRGCVD(rgb[i]);
                    break;

                case 2:
                    rgb[i] = ReColorBYCVD(rgb[i]);
                    break;
            }
        }
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        result.setRGB(0, 0, w, h, rgb, 0, w);
        return result;
    }

    private static int ReColorRGCVD(int rgb) {
        // Only for Red Green CVD
        int result;
        float hsv[] = new float[3];
        Color.RGBtoHSB((rgb & R_MASK) >> 16, (rgb & G_MASK) >> 8, rgb & B_MASK, hsv);
        int H = (int) (hsv[0] * 360);
        // Hue correction
        {
            double hueRatio;
            if (60 <= H && H <= 180) {
                hueRatio = (H - 60.) / (double) (180 - 60);
                H = (int) (120 + 60 * hueRatio);
            } else if (240 <= H && H <= 360) {
                hueRatio = (H - 240.) / (double) (360 - 240);
                H = (int) (300 + 60 * hueRatio);
            }
            hsv[0] = H / 360.f;
            result = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
        }
        double V = hsv[2] * 100;
        assert (0 <= H && H <= 360);
        // Value correction
        double deltaV;
        {
            int alpha = 0;
            int beta = 0;
            int H2 = 0;
            int H1 = 0;
            if (0 <= H && H <= 50) {
                H1 = 0;
                H2 = 50;
                alpha = 255;
                beta = 39;
                deltaV = V - ((((double) (alpha - beta) / (H2 - H1)) * (H - H1) + beta) / (255.f)) * V;
            } else if (300 <= H && H <= 350) {
                H1 = 300;
                H2 = 350;
                alpha = 44;
                beta = 255;
                deltaV = V - ((((double) (alpha - beta) / (H2 - H1)) * (H - H1) + beta) / (255.f)) * V;
            } else if (350 < H && H <= 360) {
                H1 = 350;
                H2 = 360;
                alpha = 39;
                beta = 44;
                deltaV = V - ((((double) (alpha - beta) / (H2 - H1)) * (H - H1) + beta) / (255.f)) * V;
            } else {
                deltaV = 0;
            }
        }

        int g = (result & G_MASK) >> 8;
        int r = g;
        int b = result & B_MASK;
        b = (int) (b + 1 * deltaV) % 256;

        result = 0;
        result = (r << 16) | (g << 8) | (b);
        result |= A_MASK;
        return result;
    }

    private static int ReColorBYCVD(int rgb) {
        // Only for Red Green CVD
        int result;
        float hsv[] = new float[3];
        Color.RGBtoHSB((rgb & R_MASK) >> 16, (rgb & G_MASK) >> 8, rgb & B_MASK, hsv);
        int H = (int) (hsv[0] * 360);
        // Hue correction
        {
            double hueRatio;
            if (0 <= H && H <= 60) {
                hueRatio = (H - 0.) / (double) (60 - 0);
                H = (int) (60 + 60 * hueRatio);
            } else if (180 <= H && H <= 300) {
                hueRatio = (H - 180.) / (double) (300 - 180);
                H = (int) (300 + 60 * hueRatio);
            }
            hsv[0] = H / 360.f;
            result = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
        }
        double V = hsv[2] * 100;
        assert (0 <= H && H <= 360);
        // Value correction
        double deltaV;
        {
            int alpha = 0;
            int beta = 0;
            int H2 = 0;
            int H1 = 0;
            if (70 <= H && H <= 120) {
                H1 = 70;
                H2 = 170;
                alpha = 44;
                beta = 255;
                deltaV = V - ((((double) (alpha - beta) / (H2 - H1)) * (H - H1) + beta) / (255.f)) * V;
            } else if (120 <= H && H <= 170) {
                H1 = 70;
                H2 = 170;
                alpha = 255;
                beta = 44;
                deltaV = V - ((((double) (alpha - beta) / (H2 - H1)) * (H - H1) + beta) / (255.f)) * V;
            } else {
                deltaV = 0;
            }
        }

        int r = (result & R_MASK) >> 16;
        int b = result & B_MASK;
        int g = b;
        r = (int) (r + 1 * deltaV) % 256;

        result = 0;
        result = (r << 16) | (g << 8) | (b);
        result |= A_MASK;
        return result;
    }
}
