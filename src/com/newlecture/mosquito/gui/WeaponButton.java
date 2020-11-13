package com.newlecture.mosquito.gui;

import java.awt.Graphics;
import java.awt.Image;

import com.newlecture.mosquito.gui.listener.ButtonClickedListener;

public class WeaponButton extends Button {

	public WeaponButton(String name, Image normalImg, Image pressedImg, double x, double y, int width, int height) {
		super(name, normalImg, pressedImg, x, y, width, height);
		
		this.setImgWidth(this.getWidth());
		this.setImgHeight(this.getWidth());
	}
	
}
