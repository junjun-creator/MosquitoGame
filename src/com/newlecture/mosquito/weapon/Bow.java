package com.newlecture.mosquito.weapon;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.newlecture.mosquito.service.ImageLoader;

public class Bow extends Weapon {
	   private Clip bgClip;
	   private AudioInputStream bgAis;
	
	public Bow() {

		this.setType("Bow");
		this.setDamage(30);
		this.setImg(ImageLoader.bow);
		this.setProb(0.9);
		this.setRange(2);

		this.setWidth(348);
		this.setHeight(533);
		this.setImgX(3);
		this.setImgY(3);
		this.setAttackSpeed(40); // 공격속도는 imgTempo * imgSize 보다 낮을 수 없음
		this.setImgSize(23);
		this.setImgTempo(1);
	}
	
	
	public void AttackSound() {
		try {
			bgAis = AudioSystem.getAudioInputStream(new File("res/sound/bowSound.wav"));
			bgClip = AudioSystem.getClip();

			bgClip.open(bgAis);
			bgClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
