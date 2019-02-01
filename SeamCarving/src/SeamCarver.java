import java.util.ArrayList;

public class SeamCarver
{
	private SmC_Picture pic;
	public SeamCarver(SmC_Picture pictureP)
	{
		pic=pictureP;
	}

	public SmC_Picture picture()
	{
		return pic;
	}

	public int width()
	{
		return pic.width();
	}

	public int height()
	{
		return pic.height();
	}

	public double energy(int x, int y)
	{
		if(x<0||x>pic.width()-1||y<0||y>pic.height()-1)
		{
			throw new IndexOutOfBoundsException();
		}
		if(x==0||x==pic.width()-1||y==0||y==pic.height()-1)
		{
			return 1000.0;
		}
		return Math.sqrt(delta2(x,y)[0]+delta2(x,y)[1]);
		
	}
	
	private double[] delta2(int x, int y)
	{
		double[] result= new double[2];
		//for x change x-1, x+1
		result[0]=Math.pow(pic.get(x-1, y).getRed()-pic.get(x+1, y).getRed(), 2)+Math.pow(pic.get(x-1, y).getGreen()-pic.get(x+1, y).getGreen(), 2)+
				Math.pow(pic.get(x-1, y).getBlue()-pic.get(x+1, y).getBlue(), 2);
		result[1]=Math.pow(pic.get(x, y-1).getRed()-pic.get(x, y+1).getRed(), 2)+Math.pow(pic.get(x, y-1).getGreen()-pic.get(x, y+1).getGreen(), 2)+
				Math.pow(pic.get(x, y-1).getBlue()-pic.get(x, y+1).getBlue(), 2);
		
		return result;
	}
	
	
	private int[] seam()
	{
		double[] distTo= new double[height()];
		int[] prev= new int[height()];
		
		
		double[][] energy= new double[height()][width()];
		
		
		for(int i=0; i<height(); i++)
		{
			distTo[i]=Double.POSITIVE_INFINITY;
			prev[i]=-1;
		}
		distTo[0]=0;

		int upper=-1; 
		int lower=2;
		for(int j=0; j<height()-1; j++)
		{
			for(int i=0; i<width(); i++)
			{
				if(i==0)
				{
					upper++;
				}
				if(i==width()-1)
				{
					lower--;
				}
				for(int k=upper; k<lower; k++)
				{
					double newdis= distTo[j]+energy(i+k,j+1);
					if(distTo[j+1]>newdis)
					{
						distTo[j+1]=newdis;
						prev[j+1]=j;
					}
				}
			}
		}
		
		return prev;
		
	}
	public int[] findHorizontalSeam()
	{
		throw new UnsupportedOperationException();
	}

	public int[] findVerticalSeam()
	{
		return seam();
	}

	public void removeHorizontalSeam(int[] a)
	{
		throw new UnsupportedOperationException();
	}

	public void removeVerticalSeam(int[] a)
	{
		throw new UnsupportedOperationException();
	}
	
	
}