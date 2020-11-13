package com.newlecture.mosquito.weapon;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.newlecture.mosquito.service.ImageLoader;

public class Hand extends Weapon {
	private Clip bgClip;
	private AudioInputStream bgAis;

	// public static AudioInputStream handSound;

	public Hand() {

		this.setType("Hand");
		this.setDamage(5);
		this.setImg(ImageLoader.hand);
		this.setProb(0.9);
		this.setRange(2);

		this.setWidth(180);
		this.setHeight(89);
		this.setImgX(90);
		this.setImgY(23);
		this.setAttackSpeed(40); // 공격속도는 imgTempo * imgSize 보다 낮을 수 없음
		this.setImgSize(18);
		this.setImgTempo(1);

		// this.setBgm(handSound);
	}

	public void AttackSound() {
		try {
			bgAis = AudioSystem.getAudioInputStream(new File("res/sound/hand.wav"));
			bgClip = AudioSystem.getClip();

			bgClip.open(bgAis);
			bgClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
