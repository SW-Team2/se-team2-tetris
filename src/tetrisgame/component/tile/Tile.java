package tetrisgame.component.tile;

import java.awt.image.BufferedImage;

import tetrisgame.TetrisGame;
import tetrisgame.component.IGameComponent;
import tetrisgame.enumerations.eMsg;
import tetrisgame.util.ImageLoader;

public class Tile extends IGameComponent {
	protected Tile mPubBoard[][];
	protected String mName;

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

	public void eraseAct() {
	}

	public BufferedImage getTexture() {
		return ImageLoader.getInstance().getTexture(mName);
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}
}
