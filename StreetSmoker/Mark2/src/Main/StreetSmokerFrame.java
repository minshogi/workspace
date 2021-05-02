
package Main;

import human.human;
import human.overseer;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import cigar.CigarSmoke;
import smokerpackage.smoker;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject;
import loot.graphics.DrawableObject3D;
import loot.graphics.Viewport;
import buildingpackge.building;

public class StreetSmokerFrame extends GameFrame {
	
	class LifeBar extends DrawableObject
	{
		int lifebar_width=160;
		
		public LifeBar()
		{
			x = 10;
			y = 10;
			width = 160;
			height = 20;
			image = images.GetImage("lb");
		}
		
	}
	LifeBar lb;	
	
	class GameOver extends DrawableObject
	{
		
		public GameOver()
		{
			x = 0;
			y = 0;
			width = 800;
			height = 600;
			image = images.GetImage("gameover");
		}
		
	}
	class GameWin extends DrawableObject
	{
		
		public GameWin()
		{
			x = 0;
			y = 0;
			width = 800;
			height = 600;
			image = images.GetImage("gamewin");
		}
		
	}
	GameWin gw;
	class EndPoint extends DrawableObject3D
	{
		
		public EndPoint(Image image_)
		{
			super(1400,-1400,5,100,100,image_);
		}
		
	}
	
	EndPoint endpoint;
	
	GameOver go;
	private static final long serialVersionUID = 1L;
	Viewport viewport = new Viewport(0, 0, settings.canvas_width, settings.canvas_height);
	smoker minshogi;
	map DesertMap;
	void load(String path,String name,Image[] returnImage)
	{
		for(int i=1;i<=4;i++)
		{
			images.LoadImage("image/"+path+(i+"")+".png", name+(i+""));
			returnImage[i-1] = images.GetImage(name+(i+""));
		}
	}
	
	ArrayList<building> buildings = new ArrayList<building>();

	ArrayList<overseer> overseers = new ArrayList<overseer>();
	ArrayList<human> arr2 = new ArrayList<human>();
	@Override
	public boolean Initialize() {		
		LoadColor(Color.black);
		LoadFont("돋움체 BOLD 24");

		//'카메라' 설정 부분
		viewport.pointOfView_z = 500;
		viewport.view_baseDistance = 500;

		viewport.view_minDistance = 0.1;
		viewport.view_maxDistance = 1000;
		viewport.view_width = settings.canvas_width;
		viewport.view_height = settings.canvas_height;
		
		DesertMap = new map(images.GetImage("Map"));
		endpoint = new EndPoint(images.GetImage("endpoint"));

		Image[] left = new Image[4];
		load("minshogi_left","left",left);
		Image[] front = new Image[4];
		load("minshogi_front","front",front);
		Image[] back = new Image[4];
		load("minshogi_back","back",back);
		Image[] right = new Image[4];
		load("minshogi_right","right",right);
		Image[] sback = new Image[4];
		load("smokyminshogi_back","smokyback",sback);
		Image[] sfront = new Image[4];
		load("smokyminshogi_front","smokyfront",sfront);
		Image[] sleft = new Image[4];
		load("smokyminshogi_left","smokyleft",sleft);
		Image[] sright = new Image[4];
		load("smokyminshogi_right","smokyright",sright);		

		minshogi = new smoker(front,back,left,right,sfront,sback,sleft,sright);

		viewport.children.add(DesertMap);
		viewport.children.add(minshogi);
		viewport.children.add(endpoint);



		building bd = new building(-1100,1000,images.GetImage("buildingtop12"),images.GetImage("buildingmid12"),images.GetImage("buildingbot12"));

		buildings.add(bd);

		viewport.children.add(bd.Bot);

		viewport.children.add(bd.Mid);

		viewport.children.add(bd.Top);

		

		buildings.add(new building(-400,1000,images.GetImage("buildingtopa"),images.GetImage("buildingmida"),images.GetImage("buildingbota")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(300,1000,images.GetImage("buildingtopb"),images.GetImage("buildingmidb"),images.GetImage("buildingbotb")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(920,1000,images.GetImage("buildingtop1"),images.GetImage("buildingmid1"),images.GetImage("buildingbot1")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(-1500,0,images.GetImage("buildingtop2"),images.GetImage("buildingmid2"),images.GetImage("buildingbot2")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(-870,0,images.GetImage("buildingtop3"),images.GetImage("buildingmid3"),images.GetImage("buildingbot3")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(-240,0,images.GetImage("buildingtop4"),images.GetImage("buildingmid4"),images.GetImage("buildingbot4")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(1100,200,images.GetImage("buildingtop5"),images.GetImage("buildingmid5"),images.GetImage("buildingbot5")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(1400,-700,images.GetImage("buildingtop6"),images.GetImage("buildingmid6"),images.GetImage("buildingbot6")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(-800,-1000,images.GetImage("buildingtop7"),images.GetImage("buildingmid7"),images.GetImage("buildingbot7")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(-100,-1000,images.GetImage("buildingtop8"),images.GetImage("buildingmid8"),images.GetImage("buildingbot8")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(770,-700,images.GetImage("buildingtop9"),images.GetImage("buildingmid9"),images.GetImage("buildingbot9")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);

		

		buildings.add(new building(770, -1500,images.GetImage("buildingtop10"),images.GetImage("buildingmid10"),images.GetImage("buildingbot10")));

		viewport.children.add(buildings.get(buildings.size()-1).Bot); 

		viewport.children.add(buildings.get(buildings.size()-1).Mid);

		viewport.children.add(buildings.get(buildings.size()-1).Top);
		for(int i=0;i<50;i++)
		{
			Random random = new Random();
			
			boolean flag = false;
			double newX = random.nextDouble()*3000-1500;
			double newY = random.nextDouble()*3000-1500;

			while(true)
			{
				newX = random.nextDouble()*3000-1500;
				newY = random.nextDouble()*3000-1500;
				for(int j=0;j<buildings.size();j++)
				{
					if(buildings.get(j).colCheck(newX,newY) == true)
					{
						flag = true;
						break;
					}
				}
				if(flag==true)flag=false;
				else break;
			}

			human woman = new human(newX,newY);
			Image[] Wleft = new Image[4];
			load("여자왼쪽","womenleft",Wleft);
			Image[] Wfront = new Image[4];
			load("여자앞","womenfront",Wfront);
			Image[] Wback = new Image[4];
			load("여자뒤","womenback",Wback);
			Image[] Wright = new Image[4];
			load("여자오른쪽","womenright",Wright);
			
			human student = new human(newX,newY);
			Image[] WWleft = new Image[4];
			load("beauty_left","wwomenleft",WWleft);
			Image[] WWfront = new Image[4];
			load("beauty_front","wwomenfront",WWfront);
			Image[] WWback = new Image[4];
			load("beauty_back","wwomenback",WWback);
			Image[] WWright = new Image[4];
			load("beauty_right","wwomenright",WWright);

			
			
			student.getImage(WWfront, WWback, WWleft, WWright);
			viewport.children.add(student);
			arr2.add(student);
			
			woman.getImage(Wfront, Wback, Wleft, Wright);
			viewport.children.add(woman);
			arr2.add(woman);
			
			flag = false;
			newX = random.nextDouble()*3000-1500;
			newY = random.nextDouble()*3000-1500;

			while(true)
			{
				newX = random.nextDouble()*3000-1500;
				newY = random.nextDouble()*3000-1500;
				for(int j=0;j<buildings.size();j++)
				{
					if(buildings.get(j).colCheck(newX,newY) == true)
					{
						flag = true;
						break;
					}
				}
				if(flag==true)flag=false;
				else break;
			}

			
			human om = new human(newX,newY);
			Image[] omleft = new Image[4];
			load("oldman_left ","omleft",omleft);
			Image[] omfront = new Image[4];
			load("oldman_front ","omfront",omfront);
			Image[] omback = new Image[4];
			load("oldman_back ","omback",omback);
			Image[] omright = new Image[4];
			load("oldman_right ","omright",omright);
			
			om.getImage(omfront, omback, omleft, omright);
			viewport.children.add(om);
			arr2.add(om);

			flag = false;
			newX = random.nextDouble()*3000-1500;
			newY = random.nextDouble()*3000-1500;

			while(true)
			{
				newX = random.nextDouble()*3000-1500;
				newY = random.nextDouble()*3000-1500;
				for(int j=0;j<buildings.size();j++)
				{
					if(buildings.get(j).colCheck(newX,newY) == true)
					{
						flag = true;
						break;
					}
				}
				if(flag==true)flag=false;
				else break;
			}

			
			human mm = new human(newX,newY);
			Image[] mleft = new Image[4];
			load("Man3_Left","mmleft",mleft);
			Image[] mfront = new Image[4];
			load("Man3_Front","mmfront",mfront);
			Image[] mback = new Image[4];
			load("Man3_Back","mmback",mback);
			Image[] mright = new Image[4];
			load("Man3_Right","mmright",mright);
			
			mm.getImage(mfront, mback, mleft, mright);
			viewport.children.add(mm);
			arr2.add(mm);

		}
		
		for(int i=0;i<30;i++)
		{
			Random random = new Random();
			
			boolean flag = false;
			double newX = random.nextDouble()*3000-1500;
			double newY = random.nextDouble()*3000-1500;

			while(true)
			{
				newX = random.nextDouble()*3000-1500;
				newY = random.nextDouble()*3000-1500;
				for(int j=0;j<buildings.size();j++)
				{
					if(buildings.get(j).colCheck(newX,newY) == true)
					{
						flag = true;
						break;
					}
				}
				if(flag==true)flag=false;
				else break;
			}
			overseer overs = new overseer(newX,newY);
			Image[] Hleft = new Image[4];
			load("hogod_left","hogod_left",Hleft);
			Image[] Hfront = new Image[4];
			load("hogod_front","hogod_front",Hfront);
			Image[] Hback = new Image[4];
			load("hogod_back","hogod_back",Hback);
			Image[] Hright = new Image[4];
			load("hogod_right","hogod_right",Hright);
			overs.getImage(Hfront, Hback, Hleft, Hright);
			Image[] Aleft = new Image[4];
			load("hogod-whistle_left","w_hogod_left",Aleft);
			Image[] Afront = new Image[4];
			load("hogod-whistle_front","w_hogod_front",Afront);
			Image[] Aback = new Image[4];
			load("hogod-whistle_back","w_hogod_back",Aback);
			Image[] Aright = new Image[4];
			load("hogod-whistle_right","w_hogod_right",Aright);
			overs.overseergetImage(Afront, Aback, Aleft, Aright);

			overseers.add(overs);
			viewport.children.add(overs);
		}
		lb = new LifeBar();
		return true;

	}
	int Lbcnt=0;
	int smkCount=0;
	ArrayList<CigarSmoke> arr = new ArrayList<CigarSmoke>();
	
	@Override
	public boolean Update(long timeStamp) {
		
		if(minshogi.PerfectCollision(endpoint) == true)
		{
			Main.loseGame = -1;
		}
		if(Main.PlayList == 1)
		{
			if(aflag != 1)
			{
				audios.Stop("111");
				audios.Stop("chase");
				audios.Play("often");
				aflag=1;
			}
			Main.PlayList=0;
		}
		if(Main.PlayList == 2)
		{
			if(aflag != 2)
			{
				audios.Stop("111");
				audios.Stop("often");
				audios.Play("chase");
				aflag=2;
			}
			Main.PlayList=0;
		}
		if(Main.PlayList == 3)
		{
			if(aflag != 3)
			{
				audios.Stop("chase");
				audios.Stop("often");
				audios.Play("111");
				aflag=3;
			}
			Main.PlayList=0;
		}
		
		if(inputs.buttons[4].IsPressedNow() == true)cigarcnt--;
		if(cigarcnt == -1)Main.loseGame = 1;
		
		if(inputs.buttons[4].isPressed == true)smkCount++;
		
		if(smkCount == 60)            
		{
			CigarSmoke tmp = new CigarSmoke(minshogi.pos_x,minshogi.pos_y,images.GetImage("cs"));
			arr.add(tmp);
			smkCount=0;
			viewport.children.add(tmp);
		}
		for(int i=0;i<arr.size();i++)
		{
			arr.get(i).update();
		}
		for(int i=0;i<arr2.size();i++)
		{
			arr2.get(i).update(buildings);
		}
		for(int i=0;i<overseers.size();i++)
		{
			overseers.get(i).SmokeCheck(arr,buildings,minshogi);
		}
		inputs.AcceptInputs();
		
		boolean up = inputs.buttons[0].isPressed;
		boolean right = inputs.buttons[1].isPressed;
		boolean down = inputs.buttons[2].isPressed;
		boolean left = inputs.buttons[3].isPressed;
		boolean space = inputs.buttons[4].isPressed;
		
		Lbcnt++;
		if(space==true){			
			if(Lbcnt == 10){
				if(lb.width<=300)lb.width+=2;
				Lbcnt=0;
			}   
		}
		else if(lb.width>=0){
			if(Lbcnt >= 3){
				lb.width-=1;
				Lbcnt=0;
			}
		}
		else
		{
			Main.loseGame = 1;
		}
		
		if(up == true){
			minshogi.nowdirection = smoker.direction.back;
		}
		else if(right == true){
			minshogi.nowdirection = smoker.direction.right;
		}
		else if(left == true){
			minshogi.nowdirection = smoker.direction.left;
		}
		else if(down == true){
			minshogi.nowdirection = smoker.direction.front;
		}
		else {
			minshogi.nowdirection = smoker.direction.stop;
		}
		minshogi.isSmoked = space;
		
		
		minshogi.update(buildings);
		
		

		viewport.pointOfView_x = minshogi.pos_x;
		viewport.pointOfView_y = minshogi.pos_y;
		
		// 카메라 범위 넘어가는거 설정
		if(viewport.pointOfView_x<-DesertMap.radius_x+400)viewport.pointOfView_x=-DesertMap.radius_x+400;
		if(viewport.pointOfView_y<-DesertMap.radius_y+300)viewport.pointOfView_y=-DesertMap.radius_y+300;
		if(viewport.pointOfView_x>DesertMap.radius_x-400)viewport.pointOfView_x=DesertMap.radius_x-400;
		if(viewport.pointOfView_y>DesertMap.radius_y-300)viewport.pointOfView_y=DesertMap.radius_y-300;

		return true;
	}

	int aCnt=0;
	int cigarcnt = 5;
	int aflag=0;
	int wincheck=0;
	@Override
	public void Draw(long timeStamp) {
		
		// TODO Auto-generated method stub
		//그리기 작업 시작 - 이 메서드는 Draw()의 가장 위에서 항상 호출해 주어야 함
		BeginDraw();
		
		//화면을 다시 배경색으로 채움
		ClearScreen();
		
		if( Main.loseGame == -1 || wincheck == 1)
		{
			Main.PlayList = 3;
			gw = new GameWin();
			gw.Draw(g);
			wincheck=1;
		}
		
		else if( Main.loseGame == 1 &&  wincheck == 0)
		{
			Main.PlayList = 3;
			go = new GameOver();
			go.Draw(g);
		}
		else
		{
			viewport.Draw(g);
			lb.Draw(g);
			DrawString(24, 48, "남은 담배:  %d", cigarcnt);
		}
		
		
		//DrawString(24, 48, "FPS:  %.2f", loop.GetFPS());
		
		//그리기 작업 끝 - 이 메서드는 Draw()의 가장 아래에서 항상 호출해 주어야 함
		EndDraw();
	}
	
	public StreetSmokerFrame(GameFrameSettings settings) {
		super(settings);
		inputs.BindKey(KeyEvent.VK_UP,0);
		inputs.BindKey(KeyEvent.VK_RIGHT,1);
		inputs.BindKey(KeyEvent.VK_DOWN,2);
		inputs.BindKey(KeyEvent.VK_LEFT,3);	
		inputs.BindKey(KeyEvent.VK_SPACE, 4);
		

		images.LoadImage("image/DesertMap.png", "Map");
		images.LoadImage("image/cigar smoke.png", "cs");

		images.LoadImage("image/stage2-building12 top.png", "buildingtop12");

		images.LoadImage("image/stage2-building12 middle.png", "buildingmid12");

		images.LoadImage("image/stage2-building12 bottom.png", "buildingbot12");

		

		images.LoadImage("image/stage2-building1 top.png", "buildingtop1");

		images.LoadImage("image/stage2-building1 middle.png", "buildingmid1");

		images.LoadImage("image/stage2-building1 bottom.png", "buildingbot1");

		

		images.LoadImage("image/stage2-building2 top.png", "buildingtop2");

		images.LoadImage("image/stage2-building2 middle.png", "buildingmid2");

		images.LoadImage("image/stage2-building2 bottom.png", "buildingbot2");

		

		images.LoadImage("image/stage2-building3 top.png", "buildingtop3");

		images.LoadImage("image/stage2-building3 middle.png", "buildingmid3");

		images.LoadImage("image/stage2-building3 bottom.png", "buildingbot3");

		

		images.LoadImage("image/stage2-building4 top.png", "buildingtop4");

		images.LoadImage("image/stage2-building4 middle.png", "buildingmid4");

		images.LoadImage("image/stage2-building4 bottom.png", "buildingbot4");

		

		images.LoadImage("image/stage2-building5 top.png", "buildingtop5");

		images.LoadImage("image/stage2-building5 middle.png", "buildingmid5");

		images.LoadImage("image/stage2-building5 bottom.png", "buildingbot5");

		

		images.LoadImage("image/stage2-building6 top.png", "buildingtop6");

		images.LoadImage("image/stage2-building6 middle.png", "buildingmid6");

		images.LoadImage("image/stage2-building6 bottom.png", "buildingbot6");

		

		images.LoadImage("image/stage2-building7 top.png", "buildingtop7");

		images.LoadImage("image/stage2-building7 middle.png", "buildingmid7");

		images.LoadImage("image/stage2-building7 bottom.png", "buildingbot7");

		

		images.LoadImage("image/stage2-building8 top.png", "buildingtop8");

		images.LoadImage("image/stage2-building8 middle.png", "buildingmid8");

		images.LoadImage("image/stage2-building8 bottom.png", "buildingbot8");

		

		images.LoadImage("image/stage2-building9 top.png", "buildingtop9");

		images.LoadImage("image/stage2-building9 middle.png", "buildingmid9");

		images.LoadImage("image/stage2-building9 bottom.png", "buildingbot9");

		

		images.LoadImage("image/stage2-building10 top.png", "buildingtop10");

		images.LoadImage("image/stage2-building10 middle.png", "buildingmid10");

		images.LoadImage("image/stage2-building10 bottom.png", "buildingbot10");

		

		images.LoadImage("image/building14 - top.png", "buildingtopa");

		images.LoadImage("image/building14 - middle.png", "buildingmida");

		images.LoadImage("image/building14 - bottom.png", "buildingbota");

		

		images.LoadImage("image/building15 - top.png", "buildingtopb");

		images.LoadImage("image/building15 - middle.png", "buildingmidb");

		images.LoadImage("image/building15 - bottom.png", "buildingbotb");
		images.LoadImage("image/endpoint.png", "endpoint");

		images.LoadImage("image/lifebar_red.png", "lb");
		images.LoadImage("image/gameover.png", "gameover");
		images.LoadImage("image/gamewin.png", "gamewin");
		audios.LoadAudio("Audios/often.wav", "often", 5);
		audios.LoadAudio("Audios/chase.wav", "chase", 5);
		audios.LoadAudio("Audios/1.wav", "111", 5);

	}
}
