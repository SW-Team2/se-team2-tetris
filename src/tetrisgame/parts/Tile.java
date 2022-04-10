package tetrisgame.parts;

import java.awt.image.BufferedImage;

public class Tile {
	private String mName;

	public Tile(String name) {
		mName = name;
	}

	public Tile(Tile rhs) {
		mName = rhs.mName;
	}

	public BufferedImage getTexture() {
		return ImageLoader.getInstance().getTexture(mName);
	}

	public void setName(String name) {
		mName = name;
	}
}
