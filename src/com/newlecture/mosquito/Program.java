package com.newlecture.mosquito;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.Scanner;

import com.newlecture.mosquito.entity.Stage;
import com.newlecture.mosquito.gui.PlayerHpBar;
import com.newlecture.mosquito.service.DataService;

public class Program {

	public static void main(String[] args) throws MalformedURLException {
		// 데이터를 관리하는 서비스가 먼저 만들어져야함   => 캔버스를 만들거나 이미지를 로드 할 때 데이터가 필요한 경우 때문
		DataService ds = new DataService();
		Frame frame = new GameFrame();

	}
}