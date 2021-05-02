package human;

import java.awt.Image;
import java.util.ArrayList;

import smokerpackage.smoker;
import Main.Main;
import buildingpackge.building;
import cigar.CigarSmoke;



public class overseer extends human
{
	public overseer(double x_, double y_) {
		super(x_, y_);
		pos_x = x_;
		pos_y = y_;
		pos_z = 3;
		radius_x = 25;
		radius_y = 35;
				
	}
	int cnt2=0;
	public Image[] Afront,Aback,Aleft,Aright,Anow;
	int overseerV=4;
	boolean Sflag=false;
	public void SmokeCheck(ArrayList<CigarSmoke> arr,ArrayList<building> buildings,smoker S)
	{
		if(Sflag == true)
		{
			if((S.pos_x-pos_x)*(S.pos_x-pos_x)+(S.pos_y-pos_y)*(S.pos_y-pos_y)<=200*200)
			{
				Main.PlayList = 2;
				smokechase(S);
				return;
			}
		}
		for(int i=arr.size()-1;i>=0;i--)
		{
			if(arr.get(i).trigger_remove == true)break;
			
			if((arr.get(i).pos_x-pos_x)*(arr.get(i).pos_x-pos_x)+(arr.get(i).pos_y-pos_y)*(arr.get(i).pos_y-pos_y)<=arr.get(i).radius_x*arr.get(i).radius_x)
			{
				Sflag = true;
				Main.PlayList = 2;
				smokeupdate(arr.get(i));
				return;
			}
		}		
		Sflag = false;
		Main.PlayList=1;
		update(buildings);
	}
	
	public void overseergetImage(Image[] front_,Image[] back_,Image[] left_,Image[] right_)
	{
		Afront = front_;
		Aback = back_;
		Aleft = left_;
		Aright = right_;
		image = front[0];
	}
	public void smokeupdate(CigarSmoke s)
	{
		double xDist = pos_x - s.pos_x;
		double yDist = pos_y - s.pos_y;
		if(xDist<0)xDist*=-1;
		if(yDist<0)yDist*=-1;

		if(xDist>yDist)
		{
			if(pos_x<s.pos_x)
			{
				pos_x+=overseerV;
				now = Aright;
			}
			if(pos_x>s.pos_x)
			{
				pos_x-=overseerV;
				now = Aleft;
			}
		}
		else 
		{
			if(pos_y<s.pos_y)
			{
				pos_y+=overseerV;
				now = Aback;
			}
			
			if(pos_y>s.pos_y)
			{
				pos_y-=overseerV;
				now = Afront;
			}
		} 
		cnt2++;
		image = now[cnt2/10];
		if(cnt2==39)cnt2=0;

	}
	public void smokechase(smoker s)
	{
		if(s.MiddleCollision(this) == true)
		{
			Main.loseGame = 1;
		}
		
		double xDist = pos_x - s.pos_x;
		double yDist = pos_y - s.pos_y;
		if(xDist<0)xDist*=-1;
		if(yDist<0)yDist*=-1;

		if(xDist>yDist)
		{
			if(pos_x<s.pos_x)
			{
				pos_x+=overseerV;
				now = Aright;
			}
			if(pos_x>s.pos_x)
			{
				pos_x-=overseerV;
				now = Aleft;
			}
		}
		else 
		{
			if(pos_y<s.pos_y)
			{
				pos_y+=overseerV;
				now = Aback;
			}
			
			if(pos_y>s.pos_y)
			{
				pos_y-=overseerV;
				now = Afront;
			}
		} 
		cnt2++;
		image = now[cnt2/10];
		if(cnt2==39)cnt2=0;
		
	}

}
