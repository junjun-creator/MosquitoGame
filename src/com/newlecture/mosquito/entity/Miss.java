package com.newlecture.mosquito.entity;

import java.awt.Graphics;
import java.awt.Image;

import com.newlecture.mosquito.canvas.StageCanvas;
import com.newlecture.mosquito.service.ImageLoader;

public class Miss {
	private Image img;

	private int x;
	private int y;
	private int width;
	private int height;
	private int tempo;
	private int delTime; // 미스 이미지 사라지는 시간
	private boolean isClickable = true;
	private boolean isCreatableMiss = true;

	public Miss(int x, int y) {

		setImg(ImageLoader.miss);

		this.x = x;
		this.y = y;
		width = 65;
		height = 35;
		delTime = 60;
	}

	public void paint(Graphics g) {
		if(isClickable) {
			
			int x1 = x - width / 2;
			int y1 = y - width / 2;
			int x2 = x1 + width;
			int y2 = y1 + height;
			
			g.drawImage(img, x1, y1, x2, y2, 0, 0, 0 + width, 0 + height, StageCanvas.instance);
		}
	}

	public void update() {
		tempo++;
		delTime--;
		if (tempo % 2 == 0) {
			x++;
			y--;
		}

	}
	
	

	public boolean isCreatableMiss() {
		return isCreatableMiss;
	}

	public void setCreatableMiss(boolean isCreatableMiss) {
		this.isCreatableMiss = isCreatableMiss;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getDelTime() {
		return delTime;
	}

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}
	

}