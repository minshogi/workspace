package Main;
import loot.GameFrameSettings;

public class Main {
	public static int loseGame = 0;
	public static int PlayList = 1;

	public static void main(String[] args) {
		
		GameFrameSettings settings = new GameFrameSettings();
	    /* 여기서 settings. 을 입력하여 게임 화면 관련 설정 가능 */
		
		settings.canvas_width = 800;
		settings.canvas_height = 600;			
		
		settings.gameLoop_interval_ns = 16666666;		//약 60FPS에 해당
		settings.gameLoop_use_virtualTimingMode = false;
		settings.numberOfButtons = 5;
		
	    StreetSmokerFrame window = new StreetSmokerFrame(settings); 
	    window.setVisible(true);
	}

}
