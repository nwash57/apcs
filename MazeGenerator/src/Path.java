import java.util.Arrays;
import java.util.Random;

public class Path
{
    private Random rand = new Random();
    private Maze maze;
    private int length, maxLength, lastDir, height, width;
    private int curLoc;
    private boolean generating;
    private Point[] grid;
	private boolean debug, toTxt, toPng;

    public Path(int head, int gridWidth, int gridHeight, int ml, Point[] iGrid, Maze m, boolean d, boolean txt, boolean png)
    {
        maze = m;
        generating = true;
        lastDir = -1;
        length = 1;
        grid = iGrid;
        curLoc = head;
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
        if (grid[up()].isType(0))
        {
            curLoc = up();
            grid[curLoc].setType(2);
	        length++;
	        maze.frameNum++;

            if (grid[curLoc].getX() > 0 && grid[curLoc].getY() < height - 1 && grid[downLeft()].isType(0))
                grid[downLeft()].setType(3);


            if (grid[curLoc].getX() < width - 1 && grid[curLoc].getY() < height - 1 && grid[downRight()].isType(0))
                grid[downRight()].setType(3);

            if (grid[curLoc].getY() < height - 2 && lastDir != 0 && lastDir != -1 && grid[downTwo()].isType(0))
                grid[downTwo()].setType(3);

	        if (length >= maxLength)
	        {
		        if (grid[curLoc].getX() > 0 && grid[left()].isType(0))
			        grid[left()].setType(3);
		        if (grid[curLoc].getX() < width - 1 && grid[right()].isType(0))
			        grid[right()].setType(3);
		        if (grid[curLoc].getY() > 0 && grid[up()].isType(0))
			        grid[up()].setType(3);
	        }

            lastDir = 0;
	        runDebugAndExports();
	        loading();
        }
    }

    private void moveLeft()
    {
        if (grid[left()].isType(0))
        {
            curLoc = left();
            grid[curLoc].setType(2);
	        length++;
	        maze.frameNum++;

            if (grid[curLoc].getX() < width - 1 && grid[curLoc].getY() > 0 && grid[upRight()].isType(0))
                grid[upRight()].setType(3);

            if (grid[curLoc].getX() < width - 1 && grid[curLoc].getY() < height - 1 && grid[downRight()].isType(0))
                grid[downRight()].setType(3);

            if (grid[curLoc].getX() < width - 2 && lastDir != 1 && grid[leftTwo()].isType(0) && lastDir != -1)
            {
                grid[leftTwo()].setType(3);
            }

	        if (length >= maxLength)
	        {
		        if (grid[curLoc].getY() > 0 && grid[up()].isType(0))
			        grid[up()].setType(3);
		        if (grid[curLoc].getY() < height - 1 && grid[down()].isType(0))
			        grid[up()].setType(3);
		        if (grid[curLoc].getX() > 0 && grid[left()].isType(0))
			        grid[left()].setType(3);
	        }

            lastDir = 1;
	        runDebugAndExports();
	        loading();
        }
    }

    private void moveDown()
    {
        if (grid[down()].isType(0))
        {
            curLoc = down();
            grid[curLoc].setType(2);
	        length++;
	        maze.frameNum++;

            if (grid[curLoc].getX() > 0 && grid[curLoc].getY() > 0 && grid[upLeft()].isType(0))
                grid[upLeft()].setType(3);

            if (grid[curLoc].getX() < height - 1 && grid[curLoc].getY() > 0 && grid[upRight()].isType(0))
                grid[upRight()].setType(3);

            if (grid[curLoc].getY() > 1 && lastDir != 2 && grid[upTwo()].isType(0) && lastDir != -1)
                grid[upTwo()].setType(3);

	        if (length >= maxLength)
	        {
		        if (grid[curLoc].getX() > 0 && grid[left()].isType(0))
			        grid[left()].setType(3);
		        if (grid[curLoc].getX() < width - 1 && grid[right()].isType(0))
			        grid[right()].setType(3);
		        if (grid[curLoc].getY() < height - 2 && grid[down()].isType(0))
			        grid[right()].setType(3);
	        }

            lastDir = 2;
	        runDebugAndExports();
	        loading();
        }
    }

    private void moveRight()
    {
        if (grid[right()].isType(0))
        {
            curLoc = right();
            grid[curLoc].setType(2);
	        length++;
	        maze.frameNum++;

            if (grid[curLoc].getX() > 0 && grid[curLoc].getY() > 0 && grid[upLeft()].isType(0))
                grid[upLeft()].setType(3);

            if (grid[curLoc].getX() > 0 && grid[curLoc].getY() < height - 1 && grid[downLeft()].isType(0))
                grid[downLeft()].setType(3);

            if (grid[curLoc].getX() > 1 && lastDir != 3 && grid[leftTwo()].isType(0) && lastDir != -1)
                grid[leftTwo()].setType(3);

	        if (length >= maxLength)
	        {
		        if (grid[curLoc].getY() > 0 && grid[up()].isType(0))
			        grid[up()].setType(3);
		        if (grid[curLoc].getY() < height - 1 && grid[down()].isType(0))
			        grid[right()].setType(3);
		        if (grid[curLoc].getX() < width - 2 && grid[right()].isType(0))
			        grid[right()].setType(3);
	        }

            lastDir = 3;
	        runDebugAndExports();
	        loading();
        }
    }

    private boolean isTrapped()
    {
        if (!grid[right()].isType(0) && !grid[left()].isType(0) && !grid[down()].isType(0) && !grid[up()].isType(0))
            return true;
        else return false;
    }

    private void setGenerating()
    {
        if (grid[curLoc].getX() > 0)
        {
            if (grid[left()].isType(-1))
            {
                generating = false;
            } else generating = true;
        }

        if (generating)
        {
            if (grid[curLoc].getY() > 0)
            {
                if (grid[up()].isType(-1))
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
