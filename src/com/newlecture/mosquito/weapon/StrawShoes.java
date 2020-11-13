package com.newlecture.mosquito.weapon;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.newlecture.mosquito.service.ImageLoader;

public class StrawShoes extends Weapon {
	private Clip bgClip;
	private AudioInputStream bgAis;

	public StrawShoes() {
		this.setType("StrawShoes");
		this.setDamage(10);
		this.setImg(ImageLoader.strawShoes);
		this.setProb(0.9);
		this.setRange(2);

		this.setWidth(250);
		this.setHeight(211);
		this.setImgX(60);
		this.setImgY(175);
		this.setAttackSpeed(40); // 공격속도는 imgTempo * imgSize 보다 낮을 수 없음
		this.setImgSize(11);
		this.setImgTempo(3);

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
