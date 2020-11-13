package com.newlecture.mosquito.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import com.newlecture.mosquito.service.ImageLoader;

public class PlayerHpBar {
	private int hp;
	private int x;
	private int y;
	private Image img;
	
	public PlayerHpBar(int hp) {
		this.hp = hp;
		x = 600;
		y = 900; 
		img = ImageLoader.hpBar;
	}
	
	public void paint(Graphics g) {
	
		if(hp>=30)
			g.setColor(new Color(146,207,191));
		else
			g.setColor(Color.RED);
		
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		
		double percent = (double)hp/100;
		int hpWidth = 0;
		if(percent > 0) {
			hpWidth = (int) (w * percent);
		} 
			
		g.drawRoundRect(x+3, y+2, w-7, h-5, 20, 20);
		g.drawImage(img, x, y, x+hpWidth, y+h
						, 0, 0, hpWidth, h, null);
	
		g.setColor(Color.BLACK);
		g.setFont(new Font("돋움", Font.BOLD, 20));
		g.drawString(String.valueOf(hp), x-50, y+30);
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	
}
