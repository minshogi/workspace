package cigar;
import java.awt.Image;

import loot.graphics.DrawableObject3D;

public class CigarSmoke extends DrawableObject3D
{
	public double v = 4;
	public double a = -0.01;
	public int cnt=0;
	public CigarSmoke(double x,double y,Image image_)
	{
		super(x,y,5,10,10,image_);
	}
	public void update()
	{
		if(v<0)
		{
			radius_x+=0.3;
			radius_y+=0.3;
			cnt++;
			if(cnt==100)trigger_remove = true;
			return;
		}

		if(radius_x<=0)return;
		radius_x+=v;
		radius_y+=v;
		v+=a;
		a-=0.001;
	}
}