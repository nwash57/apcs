public class Point
{
    private int x, y;
    private int[][] grid;

    public Point(int iX, int iY, int[][] iGrid)
    {
        x = iX;
        y = iY;
        grid = iGrid;
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
        return grid[x][y];
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
