import java.util.Random;

public class Path
{
    private Random rand = new Random();
    private Maze maze;
    private int length, maxLength, lastDir, height, width;
    private int curLocX, curLocY;
    private boolean generating;
    private int[][] grid;
	private boolean debug, toTxt, toPng;

    public Path(Point head, int gridWidth, int gridHeight, int ml, int[][] iGrid, Maze m, boolean d, boolean txt, boolean png)
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
	    maxLength = ml;
	    debug = d;
	    toTxt = txt;
	    toPng = png;
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
	        length++;
	        maze.frameNum++;

            if (curLocX > 0 && curLocY < height - 1)
                if (grid[curLocX - 1][curLocY + 1] == 0)
                    grid[curLocX - 1][curLocY + 1] = 3;


            if (curLocX < width - 1 && curLocY < height - 1)
                if (grid[curLocX + 1][curLocY + 1] == 0)
                    grid[curLocX + 1][curLocY + 1] = 3;

            if (curLocY < height - 2 && lastDir != 0 && lastDir != -1)
                if (grid[curLocX][curLocY + 2] == 0)
                    grid[curLocX][curLocY + 2] = 3;

	        if (length >= maxLength)
	        {
		        if (curLocX > 0 && isOpen(curLocX - 1, curLocY))
			        grid[curLocX - 1][curLocY] = 3;
		        if (curLocX < width - 1 && isOpen(curLocX + 1, curLocY))
			        grid[curLocX + 1][curLocY] = 3;
		        if (curLocY > 0 && isOpen(curLocX, curLocY - 1))
			        grid[curLocX][curLocY - 1] = 3;
	        }

            lastDir = 0;
	        runDebugAndExports();
	        loading();
        }
    }

    private void moveLeft()
    {
        if (isOpen(curLocX - 1, curLocY))
        {
            curLocX -= 1;
            grid[curLocX][curLocY] = 2;
	        length++;
	        maze.frameNum++;

            if (curLocX < width - 1 && curLocY > 0 && grid[curLocX + 1][curLocY - 1] == 0)
                grid[curLocX + 1][curLocY - 1] = 3;

            if (curLocX < width - 1 && curLocY < height - 1 && grid[curLocX + 1][curLocY + 1] == 0)
                grid[curLocX + 1][curLocY + 1] = 3;

            if (curLocX < width - 2 && lastDir != 1 && grid[curLocX + 2][curLocY] == 0 && lastDir != -1)
            {
                grid[curLocX + 2][curLocY] = 3;
            }

	        if (length >= maxLength)
	        {
		        if (curLocY > 0 && isOpen(curLocX, curLocY - 1))
			        grid[curLocX][curLocY - 1] = 3;
		        if (curLocY < height - 1 && isOpen(curLocX, curLocY + 1))
			        grid[curLocX][curLocY + 1] = 3;
		        if (curLocX > 0 && isOpen(curLocX - 1, curLocY))
			        grid[curLocX - 1][curLocY] = 3;
	        }

            lastDir = 1;
	        runDebugAndExports();
	        loading();
        }
    }

    private void moveDown()
    {
        if (isOpen(curLocX, curLocY + 1))
        {
            curLocY += 1;
            grid[curLocX][curLocY] = 2;
	        length++;
	        maze.frameNum++;

            if (curLocX > 0 && curLocY > 0 && grid[curLocX - 1][curLocY - 1] == 0)
                grid[curLocX - 1][curLocY - 1] = 3;

            if (curLocX < height - 1 && curLocY > 0 && grid[curLocX + 1][curLocY - 1] == 0)
                grid[curLocX + 1][curLocY - 1] = 3;

            if (curLocY > 1 && lastDir != 2 && grid[curLocX][curLocY - 2] == 0 && lastDir != -1)
            {
                grid[curLocX][curLocY - 2] = 3;
            }

	        if (length >= maxLength)
	        {
		        if (curLocX > 0 && isOpen(curLocX - 1, curLocY))
			        grid[curLocX - 1][curLocY] = 3;
		        if (curLocX < width - 1 && isOpen(curLocX + 1, curLocY))
			        grid[curLocX + 1][curLocY] = 3;
		        if (curLocY < height - 2 && isOpen(curLocX, curLocY + 1))
			        grid[curLocX][curLocY + 1] = 3;
	        }

            lastDir = 2;
	        runDebugAndExports();
	        loading();
        }
    }

    private void moveRight()
    {
        if (isOpen(curLocX + 1, curLocY))
        {
            curLocX += 1;
            grid[curLocX][curLocY] = 2;
	        length++;
	        maze.frameNum++;

            if (curLocX > 0 && curLocY > 0 && grid[curLocX - 1][curLocY - 1] == 0)
                grid[curLocX - 1][curLocY - 1] = 3;

            if (curLocX > 0 && curLocY < height - 1 && grid[curLocX - 1][curLocY + 1] == 0)
                grid[curLocX - 1][curLocY + 1] = 3;

            if (curLocX > 1 && lastDir != 3 && grid[curLocX - 2][curLocY] == 0 && lastDir != -1)
            {
                grid[curLocX - 2][curLocY] = 3;
            }

	        if (length >= maxLength)
	        {
		        if (curLocY > 0 && isOpen(curLocX, curLocY - 1))
			        grid[curLocX][curLocY - 1] = 3;
		        if (curLocY < height - 1 && isOpen(curLocX, curLocY + 1))
			        grid[curLocX][curLocY + 1] = 3;
		        if (curLocX < width - 2 && isOpen(curLocX + 1, curLocY))
			        grid[curLocX + 1][curLocY] = 3;
	        }

            lastDir = 3;
	        runDebugAndExports();
	        loading();
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

	private void runDebugAndExports()
	{
		if (debug)
			maze.printMaze();
		if (toTxt)
			maze.printMazeToTxt();
		if (toPng)
			maze.printMazeToImage();
	}

	private void loading()
	{
		int dotsNeeded = (int)maze.percentLoaded();
        //System.out.print("dn: " + dotsNeeded + " lp: " + maze.lastPercent);
		dotsNeeded -= maze.lastPercent;
        //System.out.print(" dnn: " + dotsNeeded);

		maze.lastPercent = (int)maze.percentLoaded();
        //System.out.println(" lpn: " + maze.lastPercent);

		for (int i = 0; i < dotsNeeded; i++)
		{
			System.out.print(".");
		}

	}
}
