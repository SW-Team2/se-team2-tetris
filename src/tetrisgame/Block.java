package tetrisgame;

import java.awt.Color;

class Block {
	private boolean mbIsFilled;
	private Color mColor;

	public Block() {
		mbIsFilled = false;
		mColor = Color.black;
	}

	public Block(boolean b) {
		mbIsFilled = b;
		mColor = Color.black;
	}

	public void deepCopy(Block in) {
		mbIsFilled = in.mbIsFilled;
		mColor = in.mColor;
	}

	public void setFill(boolean b) {
		mbIsFilled = b;
	}

	public boolean isFilled() {
		return mbIsFilled;
	}

	public void setColor(Color in) {
		mColor = in;
	}

	public Color GetColor() {
		return mColor;
	}
}
