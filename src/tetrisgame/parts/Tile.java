package tetrisgame.parts;

import java.awt.image.BufferedImage;

import tetrisgame.enumerations.eItemID;
import tetrisgame.itemmode.IIsItem;

public class Tile implements IIsItem {
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

	@Override
	public eItemID isItem() {
		return eItemID.NONE;
	}
}
