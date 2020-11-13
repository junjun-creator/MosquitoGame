package com.newlecture.mosquito.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;



import com.newlecture.mosquito.GameFrame;
import com.newlecture.mosquito.gui.Button;
import com.newlecture.mosquito.gui.GameClear;
import com.newlecture.mosquito.gui.GameOver;
import com.newlecture.mosquito.gui.TextRow;
import com.newlecture.mosquito.gui.listener.ButtonClickedAdapter;
import com.newlecture.mosquito.gui.listener.ButtonClickedListener;
import com.newlecture.mosquito.service.DataService;
import com.newlecture.mosquito.service.ImageLoader;

public class RankCanvas extends GameCanvas {

	private Image background;
	private Button backButton;

	private ArrayList<TextRow> contents;

	private LinkedHashMap<String, Integer> rankDatas;

	// 300 400
	private int leftX;
	private int rightX;
	private int y;

	private int space;
	private int rowCount;
	private boolean isDataEmpty;

	public RankCanvas() {
		// TODO Auto-generated constructor stub
		background = ImageLoader.rankBackground;
		backButton = new Button("back", ImageLoader.backBtnNormal, ImageLoader.backBtnPressed, 30, 30, 88, 85);
		backButton.addClickListener(new ButtonClickedAdapter() {
			@Override
			public void onClicked(Button target) {
				try {
					GameFrame.getInstance().switchCanvas(RankCanvas.this, MenuCanvas.class,true);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (true == backButton.contains(e.getX(), e.getY())) {
					if (backButton.getClickListener() != null) {
						backButton.getClickListener().onReleased(backButton);
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (true == backButton.contains(e.getX(), e.getY())) {
					if (backButton.getClickListener() != null) {
						backButton.getClickListener().onPressed(backButton);
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (true == backButton.contains(e.getX(), e.getY())) {
					if (backButton.getClickListener() != null) {
						backButton.getClickListener().onClicked(backButton);
					}
				}
			}
		});

		rankDatas = DataService.getInstance().getAllRankData();
		rowCount = 10;

		// 임의 데이터 넣기

		leftX = 300;
		rightX = 800;
		space = 90;
		y = 320; // content 시작 위치

		
		if(rankDatas != null) {
			// 정렬
		    List<Map.Entry<String, Integer>> entries = new LinkedList<>(rankDatas.entrySet());
		    Collections.sort(entries, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));		// value(score)기준으로 내림차순 정렬
		    
		    // 원래 있던거 비우고
		    rankDatas.clear();
		    
		    // 정렬한거 다시 넣기
		    for (Map.Entry<String, Integer> entry : entries) {
		    	rankDatas.put(entry.getKey(), entry.getValue());
		    }
		    
			if (rankDatas.size() > 0) {
				contents = new ArrayList<TextRow>();
				
				int contentY = y;
				
				int i = 0;
				for (String name : rankDatas.keySet()) {
					if(i >= 10) {
						break;
					} 
					
					String[] rowText = new String[3];
					if (i == rowCount / 2) {
						contentY = y + space;
					} else {
						contentY += space;
					}

					int x = 0;
					if ((i + 1) / 6 == 0) {
						x = leftX;
					} else {
						x = rightX;
					}
									
					rowText[0] = String.valueOf(i+1) + "위";
					rowText[1] = name; 
					rowText[2] = rankDatas.get(name) + "점";
					
					contents.add(new TextRow(rowText, x, contentY, "궁서", 40, false));
					i++;
				}
			} 
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		Image buf = this.createImage(this.getWidth(), getHeight());
		Graphics bg = buf.getGraphics();

		bg.drawImage(background, 0, 0, this); // 배경이미지

		backButton.paint(bg);

		if (contents != null) {
			for (int i = 0; i <contents.size() ; i++) {
				contents.get(i).paint(bg);
			}
		} else {
			g.setColor(Color.black);
			bg.setFont(new Font("궁서", Font.BOLD, 100));
			bg.drawString("無", 690, 650);
		}
		
		g.drawImage(buf, 0, 0, this);
	}

}
