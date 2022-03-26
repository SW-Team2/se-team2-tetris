package tetrisgame.parts;

import java.awt.Color;

public class Block {
	private Color mColor;

	public Block() {
	}

	public Block(Color colorIn) {
		mColor = colorIn;
	}

	public Color getColor() {
		return mColor;
	}
}
