package com.newlecture.mosquito;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import javax.swing.JOptionPane;

import com.newlecture.mosquito.canvas.FreeCanvas;
import com.newlecture.mosquito.canvas.GameCanvas;
import com.newlecture.mosquito.canvas.MenuCanvas;
import com.newlecture.mosquito.canvas.RankCanvas;
import com.newlecture.mosquito.canvas.StageCanvas;
import com.newlecture.mosquito.gui.IntroPanel;
import com.newlecture.mosquito.gui.PlayerHpBar;
import com.newlecture.mosquito.gui.listener.IntroListener;
import com.newlecture.mosquito.service.DataService;

public class GameFrame extends Frame {
	
	private static GameFrame instance;
	
	public static final int STAGE_MENU = 1001;
	public static final int FREE_MENU = 1002;
	public static final int EXIT_MENU = 1003;
	
	public static int canvasWidth = 1500;
	public static int canvasHeight = 1000;
	public String userName;
	

	public GameFrame() throws MalformedURLException {
		instance = this;		

		// 포커스 주기	
		this.setSize(canvasWidth, canvasHeight);
		this.setVisible(true);

		
		IntroPanel intro = new IntroPanel();		
		add(intro);

		revalidate();//재활성화(다시 유효하게 만든다)
		
		intro.setIntroListener(new IntroListener() {
			
			@Override
			public void onIntroEnd() {
				// TODO Auto-generated method stub
				
				System.out.println("시간 지남");
				intro.introSoundOff();
				
				/// 메뉴화면 전시
				MenuCanvas menuCanvas = new MenuCanvas();

				// 포커스 주기	
				menuCanvas.setFocusable(true);	// 너 포커스 받을 수 있음
				menuCanvas.requestFocus();		// 포커스 주기
				
				menuCanvas.start();				
				GameFrame.getInstance().add(menuCanvas);
				
				revalidate();//재활성화(다시 유효하게 만든다)
				

				intro.setVisible(false);
								
			}
		});
		
		Runnable sub = new Runnable() {
			public void run() {				
					try {
						System.err.println("슬립");
						Thread.sleep(44000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(intro.getIntroListener() != null) {
						intro.getIntroListener().onIntroEnd();	
						System.out.println("On Intro End");
					}					
				}
		};
		
		Thread th = new Thread(sub);
		th.start();
		
		
		// close 코드 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

	}

	// 메뉴가 바뀌었을때 호출됨/
	public void switchCanvas(GameCanvas oldCanvas, Class newCanvas, boolean checkId) throws InstantiationException, IllegalAccessException {
		boolean change = true;
		if(oldCanvas instanceof MenuCanvas && checkId) {
			
			String id="";
			while(true) {
				
				id = JOptionPane.showInputDialog(null, "사용자 아이디를 입럭하시오.", "USER ID", TEXT_CURSOR);
				
				try {
					if(DataService.getInstance().checkId(id) == false
							&& newCanvas.getName().equals("com.newlecture.mosquito.canvas.FreeCanvas")) {
						JOptionPane.showMessageDialog(null, "저장되지 않은 유저 정보입니다. 해당 아이디로 새로운 계정을 생성하였습니다. 다시 접속해주세요.", "Warning",
						        JOptionPane.WARNING_MESSAGE);
						change = false;
						break;
					}
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(id == null) {
					System.out.println(id);
					change = false;
					id="";
				}
				
				if((id.length() > 8 || id.length() < 2) && id.length() !=0)
					JOptionPane.showMessageDialog(null, "2자리 이상, 8자리 이하의 이름을 입력하시오.", "Warning",
					        JOptionPane.WARNING_MESSAGE);
				else
					break;
			}

			userName = id;
		}
		
		if(change) {
			GameCanvas canvas = (GameCanvas)newCanvas.newInstance();
			add(canvas);
			
			// 포커스 주기	
			canvas.setFocusable(true);	// 너 포커스 받을 수 있음
			canvas.requestFocus();		// 포커스 주기
			
			revalidate();//재활성화(다시 유효하게 만든다)
			
			oldCanvas.stop();		// 스레드 종료
			remove(oldCanvas);

			canvas.start();
		}
		
	}
	
	//instance 변수 / static 변수
	public static GameFrame getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
