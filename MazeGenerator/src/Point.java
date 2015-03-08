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
}
