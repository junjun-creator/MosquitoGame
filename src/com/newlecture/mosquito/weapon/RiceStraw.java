package com.newlecture.mosquito.weapon;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.newlecture.mosquito.service.ImageLoader;

public class RiceStraw extends Weapon {
	private Clip bgClip;
	private AudioInputStream bgAis;

	public RiceStraw() {
		this.setType("riceStraw");
		this.setDamage(10);
		this.setImg(ImageLoader.riceStraw);
		this.setProb(0.9);
		this.setRange(2);

		this.setWidth(120);
		this.setHeight(217);
		this.setImgX(50);
		this.setImgY(60);
		this.setAttackSpeed(40); // 공격속도는 imgTempo * imgSize 보다 낮을 수 없음
		this.setImgSize(13);
		this.setImgTempo(2);

	}

	public void AttackSound() {
		try {
			bgAis = AudioSystem.getAudioInputStream(new File("res/sound/straw.wav"));
			bgClip = AudioSystem.getClip();

			bgClip.open(bgAis);
			bgClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}