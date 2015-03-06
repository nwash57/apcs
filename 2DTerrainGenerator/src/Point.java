public class Point
{
    private int x, y;
    private int[][] grid;
    private int type;

    public Point(int iX, int iY)
    {
        x = iX;
        y = iY;
        type = 0;
    }

    public Point(int iX, int iY, int[][] iGrid)
    {
        x = iX;
        y = iY;
        grid = iGrid;
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

    public int getUp()
    {
        return grid[x][y - 1];
    }

    public int getLeft()
    {
        return grid[x - 1][y];
    }

    public int getDown()
    {
        return grid[x][y + 1];
    }

    public int getRight()
    {
        return grid[x + 1][y];
    }

    public int getUpLeft()
    {
        return grid[x - 1][y - 1];
    }

    public int getUpRight()
    {
        return grid[x + 1][y - 1];
    }

    public int getDownLeft()
    {
        return grid[x - 1][y + 1];
    }

    public int getDownRight()
    {
        return grid[x + 1][y + 1];
    }
}
