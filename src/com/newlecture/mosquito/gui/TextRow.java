package com.newlecture.mosquito.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TextRow {
	// 행에 들어갈 내용들
	private String[] texts;
	private String text;
	
	// 열 갯수
	private int colsCount;
	
	// 화면상에서의 좌표 
	private int x;
	private int y;
	
	// 박스의 크기
	private int width;
	private int height;
	
	private Font font;
	private boolean isTitle;
	

	public TextRow(String[] colsTexts, int x, int y) {			
		this(colsTexts, x, y, "궁서", 50, false);
	}
	
	public TextRow(String[] colsTexts, int x, int y, String fontName, int fontSize, boolean isTitle) {			
		String format = "";		
		
		int fontSpace = 11 - 2*(fontSize/10);
		
		
		for(int i=0 ; i<colsTexts.length ; i++) {
			format += "%s";
			for(int j=0 ; j<fontSpace ; j++) {
				format += " ";
			}	
		}
		format += "\n";		
		
		text = String.format(format, colsTexts);
		
		isTitle = false;
		int fontStyle = 0;
		if(true == isTitle) {
			fontStyle = Font.BOLD; 
		} else {
			fontStyle = Font.PLAIN;
		}
		font = new Font(fontName, fontStyle, fontSize);
		
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g) {		// bg
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(text, x, y+50);				
	}
	

	public String[] getTexts() {
		return texts;
	}

	public void setTexts(String[] texts) {
		this.texts = texts;
	}

	public int getColsCount() {
		return colsCount;
	}

	public void setColsCount(int colsCount) {
		this.colsCount = colsCount;
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

	public boolean isTitle() {
		return isTitle;
	}

	public void setTitle(boolean isTitle) {
		this.isTitle = isTitle;
	}
	
	
}
