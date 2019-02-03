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
		
		double[][] energy=new double[width()][height()];
		double[][]distTo= new double[width()][height()];
		int[] prev= new int[height()];
		for(int i=0; i<width(); i++)
		{
			for(int j=0; j<height(); j++)
			{
				energy[i][j]=energy(i,j);
				distTo[i][j]=Double.POSITIVE_INFINITY;
			}
		}
		for(int i=0; i<width(); i++)
		{
			distTo[i][0]=0.0;
		}
		for(int j=0; j<height(); j++)
		{
			prev[j]=-1;
		}
		
		int upper=-1;
		int lower=2;
		for(int j=0; j<height()-1; j++)
		{
			for(int i=0; i<width(); i++)
			{
				if(i==0)
				{
					upper=0;
					lower=2;
				}
				if(i==width()-1)
				{
					upper=-1;
					lower=1;
				}
				if(i!=0&&i!=width()-1)
				{
					upper=-1;
					lower=2;
				}
				for(int k=upper; k<lower; k++)
				{
					double newdis=distTo[i][j]+energy[i+k][j+1];
					if(distTo[i+k][j+1]>newdis)
					{
						distTo[i+k][j+1]= newdis;
						prev[j+1]=i;
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
