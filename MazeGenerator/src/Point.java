import java.util.Arrays;

public class Point
{
    private int x, y;
    private Point[] grid;
    private int type;

    public Point(int iX, int iY)
    {
        x = iX;
        y = iY;
        type = 0;
    }

    public String toString()
    {
        String s = "";

        switch (type)
        {
            case -1:
                s = Colors.GREEN + "\t@" + Colors.RESET;
                break;
            case 0:
                s = Colors.WHITE + "\t." + Colors.RESET;
                break;
            case 1:
                s = Colors.CYAN + "#" + Colors.RESET;
                break;
        }

        return s;
    }

    public boolean equals(Object o)
    {
        if (!(o instanceof Point))
            return false;
        Point p = (Point)o;
        return (this.getX() == p.getX() && this.getY() == p.getY());
    }

    public void setType(int t)
    {
        type = t;
    }

    public boolean isType(int t)
    {
        if (type == t) return true;
        else return false;
    }

	public boolean hasAdjacentOpen(Point p)
	{
		boolean b = false;
		int x = p.getX();
		int y = p.getY();

		////////////////////////////////////////////////
		//ANY SPACE THAT IS NOT A CORNER OR SIDE
		////////////////////////////////////////////////
		if (x > 0 && x < width - 1 && y > 0 && y < height - 1)
		{
			if (grid[up()].isType(0) || p.getDown() == 0 || p.getLeft() == 0 || p.getRight() == 0)
				b = true;
		}

		////////////////////////////////////////////////
		//ANY SPACE THAT IS A SIDE && NOT A CORNER
		////////////////////////////////////////////////
		else if (y == 0 && x > 0 && x < width - 1)
		{
			if (p.getDown() == 0 || p.getLeft() == 0 || p.getRight() == 0)
				b = true;
		}
		else if (x == 0 && y > 0 && y < height - 1)
		{
			if (grid[up()].isType(0) || p.getRight() == 0 || p.getDown() == 0)
				b = true;
		}
		else if (y == height - 1 && x > 0 && x < width - 1)
		{
			if (grid[up()].isType(0) || p.getRight() == 0 || p.getLeft() == 0)
				b = true;
		}
		else if (x == width - 1 && y > 0 && y < height - 1)
		{
			if (p.getLeft() == 0 || p.getDown() == 0 || grid[up()].isType(0))
				b = true;
		}

		////////////////////////////////////////////////
		//ANY SPACE THAT IS ONLY A CORNER
		////////////////////////////////////////////////
		else if (x == 0 && y == 0)
		{
			if (p.getRight() == 0 || p.getDown() == 0)
				b = true;
		}
		else if (x == 0 && y == height - 1)
		{
			if (p.getRight() == 0 || grid[up()].isType(0))
				b = true;
		}
		else if (x == width - 1 && y == 0)
		{
			if (p.getLeft() == 0 || p.getDown() == 0)
				b = true;
		}
		else if (x == width - 1 && y == height - 1)
		{
			if (p.getLeft() == 0 || grid[up()].isType(0))
				b = true;
		}
		return b;
	}

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getType()
    {
        return type;
    }

    public Point getUp()
    {
        return grid[Arrays.asList(grid).indexOf(new Point(x, y - 1))];
    }

    public Point getLeft()
    {
        return grid[Arrays.asList(grid).indexOf(new Point(x - 1, y))];
    }

    public Point getDown()
    {
        return grid[Arrays.asList(grid).indexOf(new Point(x, y + 1))];
    }

    public Point getRight()
    {
        return grid[Arrays.asList(grid).indexOf(new Point(x + 1, y))];
    }

    public Point getUpLeft()
    {
        return grid[Arrays.asList(grid).indexOf(new Point(x - 1, y - 1))];
    }

    public Point getUpRight()
    {
        return grid[Arrays.asList(grid).indexOf(new Point(x + 1, y - 1))];
    }

    public Point getDownLeft()
    {
        return grid[Arrays.asList(grid).indexOf(new Point(x - 1, y + 1))];
    }

    public Point getDownRight()
    {
        return grid[Arrays.asList(grid).indexOf(new Point(x + 1, y + 1))];
    }

	private int up() { return curLoc - width; }
	private int left() { return curLoc - 1; }
	private int down() { return curLoc + width; }
	private int right() { return curLoc + 1; }
	private int upLeft() { return curLoc - 1 - width; }
	private int upRight() { return curLoc + 1 - width; }
	private int downLeft() { return curLoc - 1 + width; }
	private int downRight() { return curLoc + 1 + width; }
	private int upTwo() { return curLoc - (2 * width); }
	private int leftTwo() { return curLoc - 2; }
	private int downTwo() { return curLoc + (2 * width); }
	private int rightTwo() { return curLoc + 2; }
}
