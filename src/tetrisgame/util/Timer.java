package tetrisgame.util;

public class Timer {
	private long mStartTime;
	private long mTotalTime;
	private long mPausedTime;
	private long mDeltaTime;

	private boolean mbPauseFlag;

	public void initialize() {
		mStartTime = System.currentTimeMillis();
		mTotalTime = getTotalTime();
		mPausedTime = 0;
		mDeltaTime = 0;

		mbPauseFlag = false;
	}

	public void tick() {
		long prevTotalTime = mTotalTime;
		mTotalTime = getTotalTime();
		mDeltaTime = mTotalTime - prevTotalTime;

		if (mbPauseFlag) {
			mPausedTime += mDeltaTime;
			mDeltaTime = 0;
		}
	}

	public boolean getPauseState() {
		return mbPauseFlag;
	}

	public float getGameTime() {
		float R = (getTotalTime() - mPausedTime) / 1000.0f;
		return R;
	}

	public float getDeltaTime() {
		float R = mDeltaTime / 1000.0f;
		return R;
	}

	public void pause() {
		mbPauseFlag = true;
	}

	public void unPause() {
		mbPauseFlag = false;
	}

	private long getTotalTime() {
		return System.currentTimeMillis() - mStartTime;
	}
}
