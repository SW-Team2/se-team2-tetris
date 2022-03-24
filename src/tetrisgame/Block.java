package tetrisgame;

import java.awt.Color;

class Block {
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
