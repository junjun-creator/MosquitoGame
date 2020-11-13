package com.newlecture.mosquito.entity;

import java.awt.Graphics;
import java.awt.Image;

import com.newlecture.mosquito.canvas.StageCanvas;
import com.newlecture.mosquito.service.DataService;
import com.newlecture.mosquito.service.ImageLoader;

public class Score {

	private Image img;
	private int x;
	private int y;
	private int width;
	private int height;
	private int score;
	

	public Score() {
		setImg(ImageLoader.scoreNumber);
		x = 1200;
		y = 50;
		width = 56;
		height = 72;
		score = 0;
		
	}

	public void paint(Graphics g) {
		// score 각 자리 숫자
		int[] scoreNum = new int[4];
		scoreNum[0] = score / 1000;
		scoreNum[1] = (score % 1000) / 100;
		scoreNum[2] = (score % 100) / 10;
		scoreNum[3] = score % 10;
		for (int i = 0; i < 4; i++) {
			g.drawImage(img, x + width * i, y, x + width * i + width, y + height, scoreNum[i] * width, 0,
					scoreNum[i] * width + width, 0 + height, StageCanvas.instance);
		}
	}

	public void update() {
		
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	
	

}
