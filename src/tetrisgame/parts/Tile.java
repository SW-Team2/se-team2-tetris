package tetrisgame.parts;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
	private Color mColor;
	private BufferedImage mTex;

	public Tile() {
		try {
			mTex = ImageIO.read(getClass().getResourceAsStream("../../res/tiles/tempblock.png"));
		} catch (IOException e) {
			// TODO:
			assert (false) : "Open File";
		}
	}

	public Tile(Color colorIn) {
		try {
			mTex = ImageIO.read(getClass().getResourceAsStream("../../res/tiles/tempblock.png"));
		} catch (IOException e) {
			// TODO:
			assert (false) : "Open File";
		}
		mColor = colorIn;
	}

	public BufferedImage getTexture() {
		return mTex;
	}

	public Color getColor() {
		return mColor;
	}
}
