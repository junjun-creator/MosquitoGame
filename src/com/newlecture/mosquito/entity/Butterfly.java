package com.newlecture.mosquito.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.canvas.StageCanvas;
import com.newlecture.mosquito.service.ImageLoader;

public class Butterfly extends Bug {

	private double lifeTime;
	private int penaltyTime = 5;
	private int deleteTimer = 60;
	private boolean isClickable = true;
	
	private Random rand = new Random();

	public Butterfly() {
		//
		super();
		this.setWidth(60);
		this.setHeight(60);

		// 실제 파일에서의 이미지 크기 (ex: 모기가 가로로 10개인 이미지에서 모기 하나당 width가 320인거임)
		this.setImgWidth(60);
		this.setImgHeight(60);

		// 방향 전환 시 필요한 이미지 간격
		// (ex : imgDirection=270이면 왼쪽방향 이미지와 오른쪽 방향 이미지 위치 차이가 270인격)
		this.setImgDirection(60);

		setImg(ImageLoader.butterfly);
		this.setDirection(0);
	}

	public void paint(Graphics g) {

		// System.out.println(this.getX() + " ,,,,," + this.getY());
		int w = (int) this.getWidth();
		int h = (int) this.getHeight();

		// System.out.println(w + " ,,,,, 너비");
		// int x1 = (int) this.getX();
		// int y1 = (int) this.getY();
		// int x2 = x1 + w;
		// int y2 = y1 + h;
		// 중심 좌표로 맞출거임
		int x1 = (int) this.getX() - w / 2;
		int y1 = (int) this.getY() - h / 2;
		int x2 = x1 + w; // + 60;
		int y2 = y1 + h;

		int movTempo = getWalkTempo();
		int movIndex = getMovIndex();
		Image img = getImg();

		double vx = getVx();
		double vy = getVy();

		// 나비 모션 변화
		if (vx != 0 || vy != 0) {
			if (movTempo == 0) {
				movIndex++;
				movIndex = movIndex % 10;

				movTempo = 6;
			} else
				movTempo--;
		}
		// 나비 이미지 방향 구현
		/*
		 * double x = getX(); double y = getY(); double dx = getDx();
		 * 
		 * if(x<dx) { direction = 60; }else { direction = 0; }
		 */

		int imgWidth = (int) getImgWidth();
		int imgHeight = (int) getImgHeight();

		int offsetX = movIndex * imgWidth;

		if (true == isAttacked() && isClickable == true) { // 공격 당했을 경우
			if (this.getCurrentDir() == 0) { // 왼쪽 방향
				g.drawImage(img, x1, y1, x2, y2, imgWidth * 2, imgHeight * 2, imgWidth * 3, imgHeight * 3,
						StageCanvas.instance);
			} else { // 오른쪽 방향
				g.drawImage(img, x1, y1, x2, y2, imgWidth, imgHeight * 2, imgWidth * 2, imgHeight * 3,
						StageCanvas.instance);
			}
		} else { // 평상시
			if (this.getCurrentDir() != 2)
				g.drawImage(img, x1, y1, x2, y2, 0 + offsetX, this.getDirection(), 0 + imgWidth + offsetX,
						this.getDirection() + imgHeight, StageCanvas.instance);
			else
				g.drawImage(img, x1, y1, x2, y2, 0, imgHeight * 2, 0 + imgWidth, 3 * imgHeight, StageCanvas.instance);
		}

		setWalkTempo(movTempo);
		setMovIndex(movIndex);
	}

	public int getDeleteTimer() {
		return deleteTimer;
	}

	public void setDeleteTimer(int deleteTimer) {
		this.deleteTimer = deleteTimer;
	}

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}
	

}
