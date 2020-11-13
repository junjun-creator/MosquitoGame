package com.newlecture.mosquito.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// 프로그램에서 사용되는 이미지를 미리 로드해놓는 클래스
// 이미지를 사용하는 클래스 곳곳에 static 생성자를 사용하여 만드는 것이 번거로워 이 클래스에서 한번에 로드함.
public class ImageLoader {
	public static Image menuStageBtnNormal;
	public static Image menuStageBtnPressed;
	public static Image menuFreeBtnNormal;
	public static Image menuFreeBtnPressed;
	public static Image menuRankBtnNormal;
	public static Image menuRankBtnPressed;
	public static Image menuExitBtnNormal;
	public static Image menuExitBtnPressed;
	public static Image menuBackground;
	public static Image mosquito;
	public static Image butterfly;
	public static Image scoreNumber;
	public static Image miss;
	public static Image gameOver;
	public static Image gameOverBtn;
	public static Image gameOverBg;
	public static Image gameClearBtn;

	public static Image timerNumber;
	public static Image timerDot;

	public static Image[] stageBackgrounds;


	public static Image stageText;
	public static Image stageNumber;
	
	//무기 이미지
	public static Image bow;
	public static Image fan;
	public static Image money;
	public static Image riceStraw;
	public static Image spear;
	public static Image strawShoes;
	public static Image hand;
	
	public static Image rankBackground;
	
	public static Image backBtnNormal;
	public static Image backBtnPressed;

	public static Image level1_weapon;
	public static Image level2_weapon;
	public static Image level3_weapon;

	public static Image hpBar;
	public static Image Levelup;

	static {
		try {
			// 동기 방식 이미지 로드.
			menuStageBtnNormal = ImageIO.read(new File("res/menu_stage_normal.png"));
			menuStageBtnPressed = ImageIO.read(new File("res/menu_stage_pressed.png"));
			menuFreeBtnNormal = ImageIO.read(new File("res/menu_free_normal.png"));
			menuFreeBtnPressed = ImageIO.read(new File("res/menu_free_pressed.png"));
			menuRankBtnNormal = ImageIO.read(new File("res/menu_rank_normal.png"));
			menuRankBtnPressed = ImageIO.read(new File("res/menu_rank_pressed.png"));
			menuExitBtnNormal = ImageIO.read(new File("res/menu_exit_normal.png"));
			menuExitBtnPressed = ImageIO.read(new File("res/menu_exit_pressed.png"));

			menuBackground = ImageIO.read(new File("res/menu_bg.jpg"));
			mosquito = ImageIO.read(new File("res/mosquito.png"));
			butterfly = ImageIO.read(new File("res/butterfly.png"));
			scoreNumber = ImageIO.read(new File("res/scoreNumber.png"));
			miss = ImageIO.read(new File("res/miss.png"));

			gameOver = ImageIO.read(new File("res/gameOver.png"));
			gameOverBtn = ImageIO.read(new File("res/gameOver.png"));
			gameOverBg = ImageIO.read(new File("res/gameOverBg.png"));
			gameClearBtn = ImageIO.read(new File("res/gameClear.jpg"));

			timerNumber = ImageIO.read(new File("res/timer_final.png"));
			timerDot = ImageIO.read(new File("res/timer_dot.png"));
			
	         int stageCount = 3;             //DataService.getInstance().getGameIntValue("default", "stageCount");
	         stageBackgrounds = new Image[stageCount];
	         for (int i = 0; i < stageCount; i++) {
	            stageBackgrounds[i] = ImageIO.read(new File("res/stage" + (i + 1) + "_bg.jpg"));
	         } 
			
			//여기부터 무기이미지
			bow = ImageIO.read(new File("res/weapon/bow.png"));
			fan = ImageIO.read(new File("res/weapon/fan.png"));
			money = ImageIO.read(new File("res/weapon/money.png"));
			riceStraw = ImageIO.read(new File("res/weapon/riceStraw.png"));
			spear = ImageIO.read(new File("res/weapon/spear.png"));
			strawShoes = ImageIO.read(new File("res/weapon/strawShoes.png"));
			hand = ImageIO.read(new File("res/weapon/hand.png"));
			
			
			// 스테이지 텍스트
			stageText = ImageIO.read(new File("res/stage_text.png"));
			stageNumber = ImageIO.read(new File("res/stage_num.png"));		// 70x70 
			
			rankBackground = ImageIO.read(new File("res/rank_bg.png"));			
			
			
			backBtnNormal = ImageIO.read(new File("res/back_normal.png"));		  
			backBtnPressed = ImageIO.read(new File("res/back_pressed.png"));		
			
			hpBar = ImageIO.read(new File("res/hp_bar.png"));
			
			Levelup = ImageIO.read(new File("res/Levelup.png"));
			
			
			level1_weapon = ImageIO.read(new File("res/level1_weapon.png"));
			level2_weapon = ImageIO.read(new File("res/level2_weapon.png"));	
			level3_weapon = ImageIO.read(new File("res/level3_weapon.png"));	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
