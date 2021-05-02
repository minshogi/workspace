package buildingpackge;

import java.awt.Image;

import loot.graphics.DrawableObject3D;


public class building 
{
	public double xx,yy,w=400,h=400;
	public class builngTop extends DrawableObject3D
	{
		builngTop(double x,double y,double w,double h,Image image_)
		{
			super(x,y,3.1,w,h,image_);

		}
	}	
	public class builngMid extends DrawableObject3D
	{
		builngMid(double x,double y,double w,double h,Image image_)
		{
			super(x,y,3.1,w,h,image_);
		}	 
	}
	public class builngBottom extends DrawableObject3D
	{
		builngBottom(double x,double y,double w,double h,Image image_)
		{
			super(x,y,2.9,w,h,image_);
		}
	}
	public boolean colCheck(double newX,double newY)
	{
		if(xx-w<=newX && newX<=xx+w && yy-h<=newY && newY<=yy+h)
		{
			return true;
		}
		return false;
	}
	public builngTop Top;
	public builngMid Mid;
	public builngBottom Bot;
	public building(double x,double y,Image topImage,Image midImage,Image botImage)
	{
 		xx = x;
 		yy = y;
		Top = new builngTop(x,y+300+15,300,25,topImage);
		Mid = new builngMid(x,y,300,300,midImage);
		Bot = new builngBottom(x,y-300-30,300,35,botImage);
	}
	
}
