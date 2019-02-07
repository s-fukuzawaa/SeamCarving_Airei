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
	
	private class Node
	{	 
		private int x;
		private int y;
		public Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		public int x()
		{
			return this.x;
		}
		public int y()
		{
			return this.y;
		}
		
	}
	
	private int[] seam()
	{
		
		double[][] energy=new double[width()][height()];
		double[][]distTo= new double[width()][height()];
		Node[][] prev= new Node[width()][height()];
		for(int i=0; i<width(); i++)
		{
			for(int j=0; j<height(); j++)
			{
				energy[i][j]=energy(i,j);
				distTo[i][j]=Double.POSITIVE_INFINITY;
				prev[i][j]=new Node(-1,-1);
			}
		}
		for(int i=0; i<width(); i++)
		{
			distTo[i][0]=0.0;
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
						prev[i+k][j+1]= new Node(i,j);
					}
					
				}
				
			}
		}
		Node a=new Node(0,height()-1);
		double temp=distTo[0][height()-1];
		for(int i=1; i<width(); i++)
		{
			if(temp>distTo[i][height()-1])
			{
				temp=distTo[i][height()-1];
				a=new Node(i,height()-1);
			}
		}
		
		Stack<Integer> help= new Stack<Integer>();
		
		
		while(prev[a.x()][a.y()].x()!=-1)
		{
			help.push(a.x());

			a= prev[a.x()][a.y()];
		}
		
		int[] result= new int[height()];
		for(int i=1; i<result.length; i++)
		{
			if(help.isEmpty())
			{
				break;
			}
			result[i]=help.pop();
		}
		if(result[1]==0)
		{
			result[0]=1;
		}
		if(result[1]==width()-1)
		{
			result[0]=width()-2;
		}
		if(result[1]!=0&&result[1]!=width()-1)
		{
			result[0]=result[1]-1;
		}
		return result;
		
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
		if(a==null||pic==null)
		{
			throw new java.lang.NullPointerException();
		}
		
		if(a.length!=width()||pic.height()<=1)
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		for(int i=0; i<a.length; i++)
		{
			if(a[i]>height()-1||a[i]<0||a[i+1]+1<a[i]||a[i+1]-1>a[i])
			{
				throw new java.lang.IllegalArgumentException();
			}
		}
		if(a[a.length-1]>height()-1||a[a.length-1]<0)
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		
		SmC_Picture h=new SmC_Picture(pic.width(),pic.height()-1);
		
		for(int j=0; j<width(); j++)
		{
			for(int i=0; i<a[j]; i++)
			{
				h.set(j, i, pic.get(j, i));
			}
		}
		
		for(int j=0; j<width(); j++)
		{
			for(int i=a[j]; i<height()-1; i++)
			{
				h.set(j, i, pic.get(j, i+1));
			}
		}
		this.pic=new SmC_Picture(h);
		
	}

	public void removeVerticalSeam(int[] a)
	{
		if(a==null||pic==null)
		{
			throw new java.lang.NullPointerException();
		}
		
		if(a.length!=height()||pic.width()<=1)
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		for(int i=0; i<a.length-1; i++)
		{
			if(a[i]>width()-1||a[i]<0||a[i+1]+1<a[i]||a[i+1]-1>a[i])
			{
				throw new java.lang.IllegalArgumentException();
			}
		}
		if(a[a.length-1]>width()-1||a[a.length-1]<0)
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		
		SmC_Picture v=new SmC_Picture(pic.width()-1, pic.height());
		for(int j=0; j<height(); j++)
		{
			for(int i=0; i<a[j]; i++)
			{
				v.set(i, j, pic.get(i, j));
			}
		}
		
		for(int j=0; j<height(); j++)
		{
			for(int i=a[j]; i<width()-1; i++)
			{
				v.set(i, j, pic.get(i+1, j));
			}
		}
		this.pic=new SmC_Picture(v);
	}
	
	
}
