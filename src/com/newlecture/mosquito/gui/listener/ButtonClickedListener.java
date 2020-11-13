package com.newlecture.mosquito.gui.listener;

import com.newlecture.mosquito.gui.Button;
import com.newlecture.mosquito.gui.GameClear;
import com.newlecture.mosquito.gui.GameOver;

public interface ButtonClickedListener {

	void onClicked(Button target);
	void onPressed(Button target);
	void onReleased(Button target);
	void onClicked(GameOver gameOver);
	void onClicked(GameClear gameClear);
	
}
