import java.util.Random;

public class Path
{
    private Random rand = new Random();
    private Maze maze;
    private int length, lastDir, height, width;
    private int curLocX, curLocY;
    private boolean generating;
    private int[][] grid;

    public Path(Point head, int gridWidth, int gridHeight, int[][] iGrid, Maze m)
    {
        maze = m;
        curLocX = head.getX();
        curLocY = head.getY();
        generating = true;
        lastDir = -1;
        length = 1;
        grid = iGrid;
        height = gridHeight;
        width = gridWidth;
    }

    /* DIRECTION KEY
    *   0
    * 1   3
    *   2
    */
    public void generateRandomPath()
    {
        generating = true;

        while (generating && !isTrapped())
        {
            move();
            setGenerating();
        }
    }

    private void move() {
        maze.printMazeToImage();
        int dir = rand.nextInt(4);

        switch (dir)
        {
            case 0:
                moveUp();
                break;
            case 1:
                moveLeft();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveRight();
                break;
        }
    }

    private void moveUp()
    {
        if (isOpen(curLocX, curLocY - 1))
        {
            curLocY -= 1;
            grid[curLocX][curLocY] = 2;

            if (curLocX > 0 && curLocY < height - 1)
                if (grid[curLocX - 1][curLocY + 1] == 0)
                    grid[curLocX - 1][curLocY + 1] = 3;


            if (curLocX < width - 1 && curLocY < height - 1)
                if (grid[curLocX + 1][curLocY + 1] == 0)
                    grid[curLocX + 1][curLocY + 1] = 3;

            if (curLocY < height - 2 && lastDir != 0 && lastDir != -1)
                if (grid[curLocX][curLocY + 2] == 0)
                    grid[curLocX][curLocY + 2] = 3;

            lastDir = 0;
        }
    }

    private void moveLeft()
    {
        if (isOpen(curLocX - 1, curLocY))
        {
            curLocX -= 1;
            grid[curLocX][curLocY] = 2;

            if (curLocX < width - 1 && curLocY > 0 && grid[curLocX + 1][curLocY - 1] == 0)
                grid[curLocX + 1][curLocY - 1] = 3;

            if (curLocX < width - 1 && curLocY < height - 1 && grid[curLocX + 1][curLocY + 1] == 0)
                grid[curLocX + 1][curLocY + 1] = 3;

            if (curLocX < width - 2 && lastDir != 1 && grid[curLocX + 2][curLocY] == 0 && lastDir != -1)
            {
                grid[curLocX + 2][curLocY] = 3;
            }

            lastDir = 1;
        }
    }

    private void moveDown()
    {
        if (isOpen(curLocX, curLocY + 1))
        {
            curLocY += 1;
            grid[curLocX][curLocY] = 2;

            if (curLocX > 0 && curLocY > 0 && grid[curLocX - 1][curLocY - 1] == 0)
                grid[curLocX - 1][curLocY - 1] = 3;

            if (curLocX < height - 1 && curLocY > 0 && grid[curLocX + 1][curLocY - 1] == 0)
                grid[curLocX + 1][curLocY - 1] = 3;

            if (curLocY > 1 && lastDir != 2 && grid[curLocX][curLocY - 2] == 0 && lastDir != -1)
            {
                grid[curLocX][curLocY - 2] = 3;
            }

            lastDir = 2;
        }

    }

    private void moveRight()
    {
        if (isOpen(curLocX + 1, curLocY))
        {
            curLocX += 1;
            grid[curLocX][curLocY] = 2;

            if (curLocX > 0 && curLocY > 0 && grid[curLocX - 1][curLocY - 1] == 0)
                grid[curLocX - 1][curLocY - 1] = 3;

            if (curLocX > 0 && curLocY < height - 1 && grid[curLocX - 1][curLocY + 1] == 0)
                grid[curLocX - 1][curLocY + 1] = 3;

            if (curLocX > 1 && lastDir != 3 && grid[curLocX - 2][curLocY] == 0 && lastDir != -1)
            {
                grid[curLocX - 2][curLocY] = 3;
            }

            lastDir = 3;
        }
    }

    public boolean isOpen(int x, int y)
    {
        boolean b = false;

        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            if (grid[x][y] == 0)
                b = true;
            else b = false;
        }
        return b;
    }

    private boolean isTrapped()
    {
        if (!isOpen(curLocX + 1, curLocY) && !isOpen(curLocX - 1, curLocY) && !isOpen(curLocX, curLocY + 1) && !isOpen(curLocX, curLocY - 1))
            return true;
        else return false;
    }

    private void setGenerating()
    {
        if (curLocX > 0)
        {
            if (grid[curLocX - 1][curLocY] == -1)
            {
                generating = false;
            } else generating = true;
        }

        if (generating)
        {
            if (curLocY > 0)
            {
                if (grid[curLocX][curLocY - 1] == -1)
                {
                    generating = false;
                } else generating = true;
            }
        }
    }
}
