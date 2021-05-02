package human;


import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import loot.graphics.DrawableObject3D;
import buildingpackge.building;

public class human extends DrawableObject3D
{

	public Image[] front,back,left,right,now;
	static Random random = new Random();
	int cnt=0;
	int cnt2=0;
	int a=random.nextInt()%5;
	double v=2;
	public human(double x_,double y_)
	{
		super(x_,y_,3,25,35);	
	}
	
	enum direction
	{
		front,back,right,left,stop
	}

	direction nowdirection= direction.right,before = direction.stop;
	
	public void getImage(Image[] front_,Image[] back_,Image[] left_,Image[] right_)
	{
		front = front_;
		back = back_;
		left = left_;
		right = right_;
		image = front[0];
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
	
	public void update(ArrayList<building> buildings)
	{
		
		cnt2++;
		boolean flag = false;
		
		if(cnt2==20)
		{
			cnt2=0;
			
			do
			{
				a=random.nextInt()%5;
				if(a == 0)nowdirection = direction.back;
				if(a == 1)nowdirection = direction.front;
				if(a == 2)nowdirection = direction.left;
				if(a == 3)nowdirection = direction.right;
				if(a == 4)nowdirection = direction.stop;
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
				if(a == 4)break;
				flag = false;
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
			}while(flag != false);
		}
		if(nowdirection == direction.stop)
		{
			image = front[0];
			cnt=0;
		}
		if(before != nowdirection)
		{
			if(nowdirection == direction.front)now = front;
			if(nowdirection == direction.back)now = back;
			if(nowdirection == direction.right)now = right;
			if(nowdirection == direction.left)now = left;
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
		flag = false;
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
