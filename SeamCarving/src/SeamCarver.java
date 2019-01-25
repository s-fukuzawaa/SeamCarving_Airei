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
	public int[] findHorizontalSeam()
	{
		throw new UnsupportedOperationException();
	}

	public int[] findVerticalSeam()
	{
		throw new UnsupportedOperationException();
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