package tetrisgame.parts.component;

import java.awt.image.BufferedImage;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.ImageLoader;

public class Tile extends IGameComponent {
	private Tile mPubBoard[][];
	private String mName;

	public Tile(TetrisGame g, Tile[][] gb, String name) {
		super(g);
		mPubBoard = gb;
		mName = name;
	}

	public Tile(Tile rhs) {
		this(rhs.mPubGame, rhs.mPubBoard, rhs.mName);
	}

	@Override
	public void update(float deltaTime, int userInput) {
	}

	@Override
	public void react(eMsg msg) {
	}

	public BufferedImage getTexture() {
		return ImageLoader.getInstance().getTexture(mName);
	}

	public void setName(String name) {
		mName = name;
	}
}
