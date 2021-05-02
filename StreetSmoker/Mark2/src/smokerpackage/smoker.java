package smokerpackage;

import java.awt.Image;
import java.util.ArrayList;

import loot.graphics.DrawableObject3D;

public class smoker  extends DrawableObject3D
{
	public Image[] front,back,left,right;
	public Image[] smokefront,smokeback,smokeleft,smokeright;

	public Image[] now;
	double v;
	public direction nowdirection;
	public direction before = direction.stop;
	public boolean isSmoked;
	
	public smoker(Image[] front_,Image[] back_,Image[] left_,Image[] right_,
			Image[] smokefront_,Image[] smokeback_,Image[] smokeleft_,Image[] smokeright_)
	{
		super(-1400,1400,3,25,35);
		front = front_;
		back = back_;
		left = left_;
		right = right_;
		smokefront = smokefront_;
		smokeback = smokeback_;
		smokeleft = smokeleft_;
		smokeright = smokeright_;
		
		v = 3;
		image = front[0];
	}
		
	public enum direction
	{
		front,back,right,left,stop
	}

	public boolean  MiddleCollision(DrawableObject3D ob)
	{
		if(ob.pos_x-ob.radius_x<pos_x && pos_x<ob.pos_x+ob.radius_x)
			if(ob.pos_y-ob.radius_y<pos_y && pos_y<ob.pos_y+ob.radius_y)
				return true;
		return false;
	}
	
	public boolean  PerfectCollision(DrawableObject3D ob)
	{
		if(ob.pos_x-ob.radius_x<pos_x-radius_x && pos_x-radius_x<ob.pos_x+ob.radius_x)
			if(ob.pos_y-ob.radius_y<pos_y-radius_y && pos_y-radius_y<ob.pos_y+ob.radius_y)
				return true;
		if(ob.pos_x-ob.radius_x<pos_x+radius_x && pos_x+radius_x<ob.pos_x+ob.radius_x)
			if(ob.pos_y-ob.radius_y<pos_y-radius_y && pos_y-radius_y<ob.pos_y+ob.radius_y)
				return true;
		if(ob.pos_x-ob.radius_x<pos_x-radius_x && pos_x-radius_x<ob.pos_x+ob.radius_x)
			if(ob.pos_y-ob.radius_y<pos_y+radius_y && pos_y+radius_y<ob.pos_y+ob.radius_y)
				return true;
		if(ob.pos_x-ob.radius_x<pos_x+radius_x && pos_x+radius_x<ob.pos_x+ob.radius_x)
			if(ob.pos_y-ob.radius_y<pos_y+radius_y && pos_y+radius_y<ob.pos_y+ob.radius_y)
				return true;
		return false;
	}
	
	int cnt = 0;
	boolean nowSmoked = false;
	
	public void update(ArrayList<buildingpackge.building> buildings)
	{
		if(nowSmoked == false && nowdirection == direction.stop)
		{
			image = front[0];
			cnt=0;
		}
		if(nowSmoked == true && nowdirection == direction.stop)
		{
			image = smokefront[0];
			cnt=0;
		}
			
		if(nowSmoked==false && isSmoked == true)
		{
			if(nowdirection == direction.front)now = smokefront;
			if(nowdirection == direction.back)now = smokeback;
			if(nowdirection == direction.right)now = smokeright;
			if(nowdirection == direction.left)now = smokeleft;					
			nowSmoked = true;
		}
		if(nowSmoked==true && isSmoked == false)
		{
			if(nowdirection == direction.front)now = front;
			if(nowdirection == direction.back)now = back;
			if(nowdirection == direction.right)now = right;
			if(nowdirection == direction.left)now = left;				
			nowSmoked = false;
		}
		if(before != nowdirection)
		{
			if(nowdirection == direction.front)now = front;
			if(nowdirection == direction.back)now = back;
			if(nowdirection == direction.right)now = right;
			if(nowdirection == direction.left)now = left;
			if(nowSmoked == true)
			{
				if(nowdirection == direction.front)now = smokefront;
				if(nowdirection == direction.back)now = smokeback;
				if(nowdirection == direction.right)now = smokeright;
				if(nowdirection == direction.left)now = smokeleft;
			}
			cnt=0;
			before = nowdirection;
		}
		else if (nowdirection != direction.stop){
			cnt++;
			image = now[cnt/10];
			if(cnt==39)cnt=0;
		}
		if(nowdirection == direction.front)
		{
			pos_y-=v;
		}
		if(nowdirection == direction.back)
		{
			pos_y+=v;
		}
		if(nowdirection == direction.right)
		{
			pos_x+=v;
		}
		if(nowdirection == direction.left)
		{
			pos_x-=v;	
		}
		boolean flag = false;
		for(int i=0;i<buildings.size();i++)
		{
			if(MiddleCollision(buildings.get(i).Top)==true)flag = true;
			if(MiddleCollision(buildings.get(i).Bot)==true)flag = true;
			if(PerfectCollision(buildings.get(i).Mid)==true)flag = true;
		}
		if(flag == true)
		{
			if(nowdirection == direction.front)
			{
				pos_y+=v;
			}
			if(nowdirection == direction.back)
			{
				pos_y-=v;
			}
			if(nowdirection == direction.right)
			{
				pos_x-=v;
			}
			if(nowdirection == direction.left)
			{
				pos_x+=v;	
			}
		}
	}
	
}
