package com.newlecture.mosquito.weapon;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.newlecture.mosquito.service.ImageLoader;

public class Money extends Weapon {
	private Clip bgClip;
	private AudioInputStream bgAis;

	public Money() {
		this.setType("Money");
		this.setDamage(20);
		this.setImg(ImageLoader.money);
		this.setProb(0.9);
		this.setRange(2);

		this.setWidth(240);
		this.setHeight(223);
		this.setImgX(10);
		this.setImgY(10);
		this.setAttackSpeed(40); // 공격속도는 imgTempo * imgSize 보다 낮을 수 없음
		this.setImgSize(21);
		this.setImgTempo(1);
	}

	public void AttackSound() {
		try {
			bgAis = AudioSystem.getAudioInputStream(new File("res/sound/coin.wav"));
			bgClip = AudioSystem.getClip();

			bgClip.open(bgAis);
			bgClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
