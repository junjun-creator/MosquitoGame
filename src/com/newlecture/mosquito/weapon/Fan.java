package com.newlecture.mosquito.weapon;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.newlecture.mosquito.canvas.StageCanvas;
import com.newlecture.mosquito.service.ImageLoader;

public class Fan extends Weapon {
	private Clip bgClip;
	private AudioInputStream bgAis;

	public Fan() {
		this.setType("Fan");
		this.setDamage(20);
		this.setImg(ImageLoader.fan);
		this.setProb(0.9);
		this.setRange(2);

		this.setWidth(100);
		this.setHeight(136);
		this.setImgX(50);
		this.setImgY(40);
		this.setAttackSpeed(40); // 공격속도는 imgTempo * imgSize 보다 낮을 수 없음
		this.setImgSize(23);
		this.setImgTempo(1);
	}

	public void AttackSound() {
		try {
			bgAis = AudioSystem.getAudioInputStream(new File("res/sound/fan.wav"));
			bgClip = AudioSystem.getClip();

			bgClip.open(bgAis);
			bgClip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
