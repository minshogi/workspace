package Main;
import loot.GameFrameSettings;

public class Main {
	public static int loseGame = 0;
	public static int PlayList = 1;

	public static void main(String[] args) {
		
		GameFrameSettings settings = new GameFrameSettings();
	    /* ���⼭ settings. �� �Է��Ͽ� ���� ȭ�� ���� ���� ���� */
		
		settings.canvas_width = 800;
		settings.canvas_height = 600;			
		
		settings.gameLoop_interval_ns = 16666666;		//�� 60FPS�� �ش�
		settings.gameLoop_use_virtualTimingMode = false;
		settings.numberOfButtons = 5;
		
	    StreetSmokerFrame window = new StreetSmokerFrame(settings); 
	    window.setVisible(true);
	}

}
