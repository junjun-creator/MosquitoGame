package com.newlecture.mosquito.weapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;

import com.newlecture.mosquito.canvas.StageCanvas;
import com.newlecture.mosquito.entity.Bug;
import com.newlecture.mosquito.service.ImageLoader;

public abstract class Weapon {// 추상클래스 구현

	private String type;
	private int damage;
	private double prob;
	private Image img;
	private boolean isClicked = false;
	private int range;

	private int width;
	private int height;
	private int x;
	private int y;

	// 무기 이미지 그려지는 좌표
	private int imgX;
	private int imgY;

	private int attackDelay; // 공격 딜레이
	private int attackSpeed; // 공격 속도
	private int imgIndex; // 무기 이미지 순서
	private int imgSize; // 무기 이미지 수
	private boolean imgLoading; // 무기 이미지 움직이는 중인지 판단
	private int imgTempoCnt; // 이미지 넘어가는 속도 카운트
	private int imgTempo; // 이미지 넘어가는 속도
	
	private AudioInputStream Bgm;

	public void paint(Graphics g) {

		g.setColor(Color.GREEN);
		g.drawRect(x - range, y - range, range * 2, range * 2);
		g.drawImage(img, x - imgX, y - imgY, x - imgX + width, y - imgY + height, width * imgIndex, 0,
				width * imgIndex + width, 0 + height, StageCanvas.instance);
	}

	public void update() {
		attackDelay++;

		if (imgLoading) { // 마우스 클릭되면 무기 이미지 움직임 시작
			imgTempoCnt++;
			if (imgTempoCnt % imgTempo == 0) {
				imgIndex++;
				if (imgIndex > imgSize - 1) {
					imgLoading = false;
					imgIndex = 0;
				}
			}
		}
	}

	public boolean isClickable() {
		if (attackDelay > imgSize*imgTempo) { // 이미지가 모두 넘어가야 공격가능
			attackDelay = 0;
			return true;
		} else
			return false;
	}

	// 웨폰의 공격 범위에 벌레가 들어왔는지 판단하는 메소드
	public boolean isAttackRange(Bug bug) {
		boolean isIntersect = false;

		double bX1 = bug.getX() - bug.getWidth() / 2;
		double bY1 = bug.getY() - bug.getHeight() / 2;
		double bX2 = bug.getX() + bug.getWidth() / 2;
		double bY2 = bug.getY() + bug.getHeight() / 2;

		// weapon의 공격 범위
		double mX1 = x - range;
		double mY1 = y - range;
		double mX2 = x + range;
		double mY2 = y + range;

		if (((bX1 <= mX1 && mX1 <= bX2) || (bX1 <= mX2 && mX2 <= bX2))
				&& ((bY1 <= mY1 && mY1 <= bY2) || (bY1 <= mY2 && mY2 <= bY2))) {
			isIntersect = true;
		} else {
			isIntersect = false;
		}

		return isIntersect;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setIsClicked(boolean isClicked) {// 클릭 쿨타임 설정
		this.isClicked = isClicked;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getImgSize() {
		return imgSize;
	}

	public void setImgSize(int imgSize) {
		this.imgSize = imgSize;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public boolean isImgLoading() {
		return imgLoading;
	}

	public void setImgLoading(boolean imgLoading) {
		this.imgLoading = imgLoading;
	}

	public int getImgTempo() {
		return imgTempo;
	}

	public void setImgTempo(int imgTempo) {
		this.imgTempo = imgTempo;
	}

	public int getImgX() {
		return imgX;
	}

	public void setImgX(int imgX) {
		this.imgX = imgX;
	}

	public int getImgY() {
		return imgY;
	}

	public void setImgY(int imgY) {
		this.imgY = imgY;
	}

	public AudioInputStream getBgm() {
		return Bgm;
	}

	public void setBgm(AudioInputStream bgm) {
		Bgm = bgm;
	}

	public void AttackSound() {
	
		
	}
	
	

}
