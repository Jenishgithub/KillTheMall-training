package com.edu4java.android.killthemall;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
	private static final int BMP_COLUMNS = 3;
	private static final int BMP_ROWS = 4;
	// direction = 0 up, 1 left, 2 down, 3 right,
	// animation = 3 back, 1 left, 0 front, 2 right
	int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
	private int x;
	private int y;
	private int xSpeed;
	private int ySpeed;
	private GameView gameView;
	private Bitmap bmp;
	private int height;
	private int width;
	private int currentFrame;

	public Sprite(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		this.width = bmp.getWidth() / BMP_COLUMNS;
		this.height = bmp.getHeight() / BMP_ROWS;
		Random rnd = new Random();
		x = rnd.nextInt(gameView.getWidth() - width);
		y = rnd.nextInt(gameView.getHeight() - height);
		xSpeed = rnd.nextInt(10) - 5;
		ySpeed = rnd.nextInt(10) - 5;
	}

	private void update() {
		if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
			xSpeed = -xSpeed;

		}

		x = x + xSpeed;
		if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
			ySpeed = -ySpeed;

		}
		y = y + ySpeed;
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}

	public void onDraw(Canvas canvas) {
		update();
		int srcX = currentFrame * width;
		int srcY = getAnimationRow() * height;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bmp, src, dst, null);
	}

	// direction = 0 up, 1 left, 2 down, 3 right,
	// animation = 3 back, 1 left, 0 front, 2 right
	private int getAnimationRow() {
		double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
		int direction = (int) Math.round(dirDouble) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];

	}

	public boolean isCollition(float x2, float y2) {
		// TODO Auto-generated method stub
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}
}
