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
		double temp=Double.POSITIVE_INFINITY;
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
				double temp2=distTo[i][j]+energy[i][j+1];
				int track=i;
				for(int k=upper; k<lower; k++)
				{
					double newdis=distTo[i][j]+energy[i+k][j+1];
					if(distTo[i+k][j+1]>newdis)
					{
						distTo[i+k][j+1]= newdis;
					}
					if(newdis<temp2)
					{
						temp2=newdis;
						track=i+k;
					}
					
				}
				if(temp>temp2)
				{
					temp=temp2;
					prev[j+1]=track;
				}
				
			}
			temp=Double.POSITIVE_INFINITY;
		}
		
		
		/*double mindis=distTo[0][height()-1];
		int end=0;
		for(int i=1; i< width(); i++)
		{
			if(distTo[i][height()-1]<mindis)
			{
				mindis=distTo[i][height()-1];
				end=i;
			}
		}
		prev[height()-1]=end;
		/*(for(int i=height()-2; i>-1; i--)
		{
			if(end==0)
			{
				upper=0;
				lower=2;
			}
			if(end==width()-1)
			{
				upper=-1;
				lower=1;
			}
			if(end!=0&&end!=width()-1)
			{
				upper=-1;
				lower=2;
			}
			mindis=distTo[end][i];
			int a=end;
			prev[i]=end;
			for(int k=upper; k<lower; k++)
			{
				if(distTo[end+k][i]<mindis)
				{
					prev[i]=end+k;
					a=end+k;
				}
			}
			end=a;
		}*/
		if(prev[1]==width()-1)
		{
			prev[0]=width()-2;
		}
		if(prev[1]==0)
		{
			prev[0]=1;
		}
		if(prev[1]!=width()-1&&prev[1]!=0)
		{
			prev[0]=prev[1]-1;
		}
		return prev;
		
	}
	public int[] findHorizontalSeam()
	{
		SmC_Picture end= new SmC_Picture(pic);
		SmC_Picture picture=new SmC_Picture(pic.height(),pic.width());
		for(int i=0; i<picture.width(); i++)
		{
			for(int j=0; j<picture.height(); j++)
			{
				picture.set(i, j, pic.get(j, i));
			}
		}
		
		this.pic=picture;
		
		int[] result=findVerticalSeam();
		
		this.pic=end;
		
		return result;
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
