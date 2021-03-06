import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;

/* GRID KEY
 * -1 = end
 * 0 = open
 * 1 = start
 * 2 = path
 * 3 = wall
*/

public class Maze
{
    private int fileNum = 0;

    private Random rand = new Random();
    private int[][] grid;
    private int width, height;
	private int pathLength;
    private Point curLoc;

	private boolean debug, toTxt, toPng;

	public int frameNum = 0;
    public int lastPercent = 0;

    public Maze(int iWidth, int iHeight, int iPathLength, boolean d, boolean txt, boolean png)
    {
        width = iWidth;
        height = iHeight;
	    pathLength = iPathLength;
        grid = new int[width][height];
	    debug = d;
	    toTxt = txt;
	    toPng = png;
    }

    public void generateGrid()
    {
        generateStart();
        grid[curLoc.getX()][curLoc.getY()] = 1;
        grid[0][0] = -1;
    }

    private void generateStart()
    {
        int x = 0, y = 0;

        switch (rand.nextInt(2))
        {
            case 0:
                x = rand.nextInt(width);
                if (x == 0)
                    y = rand.nextInt(height - 1) + 1;
                else y = rand.nextInt(height);
                break;
            case 1:
                y = rand.nextInt(height);
                if (y == 0)
                    x = rand.nextInt(width - 1) + 1;
                else x = rand.nextInt(width);
                break;
        }

        curLoc = new Point(x, y, grid);
    }

    public void fillGrid()
    {
	    try
	    {
		    Path path = new Path(curLoc, width, height, pathLength, grid, this, debug, toTxt, toPng);
            path.generateRandomPath();


		    if (hasOpenSpace())
		    {
                //printMazeToTxt();
			    selectNewHead();
			    if (curLoc != null)
			        fillGrid();
		    }
	    }
        catch (Exception e)
        {
	        System.out.println("ERROR IN METHOD fillGrid()");
        }
    }

	public void fillOpen()
	{
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				Point p = new Point(x, y, grid);

				if (p.getType() == 0)
					grid[p.getX()][p.getY()] = 3;
			}
		}
	}

    private void selectNewHead()
    {
	    ArrayList<Point> validPoints = new ArrayList<Point>();
	    ArrayList<Point> invalidPoints = new ArrayList<Point>();

	    for (int h = 0; h < height; h++)
	    {
		    for (int w = 0; w < width; w++)
		    {
			    Point p = new Point(w, h, grid);

			    if (hasAdjacentOpen(p) && !hasMoreThanXAdjacentPaths(p, 1) && !hasAdjacentStart(p) && p.getType() == 3)
			    {
				    validPoints.add(p);
			    }
			    else if (p.getType() == 3)
				    invalidPoints.add(p);
		    }
	    }

	    if (validPoints.size() > 0)
	    {
		    curLoc = validPoints.get(rand.nextInt(validPoints.size()));
		    grid[curLoc.getX()][curLoc.getY()] = 2;
	    }

	    if (invalidPoints.size() == wallCount())
		    curLoc = null;
    }

    public void fillExtraPath()
    {
        for (int h = 0; h < height; h++)
        {
            for (int w = 0; w < width; w++)
            {
                Point p = new Point(w, h, grid);

                if (hasWallCornerAndOppositeCornerPath(p) && grid[w][h] == 2)
                {
                    grid[w][h] = 3;
                }

            }
        }
    }

    public void printMazeToImage()
    {
        try
        {
            int lineMultiplier = 1;
            String s = String.format("%05d", fileNum);

            StringBuilder sb2 = new StringBuilder();
            sb2.append("maze-");
            sb2.append(s);
            sb2.append(".png");

            StringBuilder sb = new StringBuilder();
            BufferedImage bi = new BufferedImage(15 + (15 * width), 15 + (15 * height), BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g = bi.createGraphics();
            g.setFont(new Font("Monospaced", Font.PLAIN, 12));

            for (int i = 0; i < height; i++)
            {
                sb = new StringBuilder();

                for (int j = 0; j < width; j++)
                {
                    switch (grid[j][i])
                    {
                        case -1:
                            sb.append(" $");
                            break;
                        case 0:
                            sb.append(" .");
                            break;
                        case 1:
                            sb.append(" @");
                            break;
                        case 2:
                            sb.append("  ");
                            break;
                        case 3:
                            sb.append(" #");
                            break;
                    }
                }
                g.drawString(sb.toString(), 15, 15 * lineMultiplier);
                lineMultiplier++;
            }

            fileNum++;

            ImageIO.write(bi, "PNG", new File("/home/nathan/IdeaProjects/apcs/MazeGenerator/png_output/" + sb2.toString()));
        }
        catch (Exception e)
        {

        }
    }

    public void printMazeToTxt()
    {
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("maze-");
            String s = String.format("%03d", fileNum);
            sb.append(s);
            sb.append(".txt");

            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("/home/nathan/IdeaProjects/apcs/MazeGenerator/txt_output/" + sb.toString()), "utf-8"));

            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    switch (grid[j][i])
                    {
                        case -1:
                            w.write("$ ");
                            break;
                        case 0:
                            w.write(". ");
                            break;
                        case 1:
                            w.write("@ ");
                            break;
                        case 2:
                            w.write("  ");
                            break;
                        case 3:
                            w.write("# ");
                            break;
                    }
                }
                w.newLine();
            }
            w.close();
            fileNum++;
        }
        catch (Exception e)
        {

        }
    }

    public void printMaze()
    {
        System.out.println();
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                switch (grid[j][i])
                {
                    case -1:
                        System.out.print(Colors.GREEN + "\t$" + Colors.RESET);
                        break;
                    case 0:
                        System.out.print(Colors.WHITE + "\t." + Colors.RESET);
                        break;
                    case 1:
                        System.out.print(Colors.PURPLE + "\t@" + Colors.RESET);
                        break;
                    case 2:
                        System.out.print(Colors.YELLOW + "\t+" + Colors.RESET);
                        break;
                    case 3:
                        System.out.print(Colors.BLACK + "\t#" + Colors.RESET);
                        break;
                }
            }
            System.out.println("\n");
        }
    }

    private boolean hasOpenSpace()
    {
        boolean b = false;

        for (int[] i : grid)
        {
            for (int j : i)
            {
                if (j == 0)
                {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

	private int wallCount()
	{
		int numWalls = 0;

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				Point p = new Point(x, y, grid);

				if (p.getType() == 3)
					numWalls++;
			}
		}

		return numWalls;
	}

    private boolean hasPathWithXAdjacentPaths(int num)
    {
        boolean b = false;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
	            Point p = new Point(x, y, grid);
                System.out.println("test: " + p.getUp());

	            if (hasMoreThanXAdjacentPaths(p, num) && p.getType() == 2)
	            {
		            b = true;
		            break;
	            }
            }
        }

        return b;
    }

	public double percentLoaded()
	{
		double area = width * height;
		double closed = 0;
		double percent = 0;

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
			{
				Point p = new Point(x, y, grid);
				if (p.getType() != 0)
				{
					closed++;
					percent = (closed * 100)/ area;
				}
			}
		//System.out.println("test: " + (int)percent);
		return percent;
	}

    private boolean hasAdjacentOpen(Point p)
    {
        boolean b = false;
        int x = p.getX();
        int y = p.getY();

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS NOT A CORNER OR SIDE
        ////////////////////////////////////////////////
        if (x > 0 && x < width - 1 && y > 0 && y < height - 1)
        {
            if (p.getUp() == 0 || p.getDown() == 0 || p.getLeft() == 0 || p.getRight() == 0)
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
            if (p.getUp() == 0 || p.getRight() == 0 || p.getDown() == 0)
                b = true;
        }
        else if (y == height - 1 && x > 0 && x < width - 1)
        {
            if (p.getUp() == 0 || p.getRight() == 0 || p.getLeft() == 0)
                b = true;
        }
        else if (x == width - 1 && y > 0 && y < height - 1)
        {
            if (p.getLeft() == 0 || p.getDown() == 0 || p.getUp() == 0)
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
            if (p.getRight() == 0 || p.getUp() == 0)
                b = true;
        }
        else if (x == width - 1 && y == 0)
        {
            if (p.getLeft() == 0 || p.getDown() == 0)
                b = true;
        }
        else if (x == width - 1 && y == height - 1)
        {
            if (p.getLeft() == 0 || p.getUp() == 0)
                b = true;
        }
        return b;
    }

    private boolean hasMoreThanXAdjacentPaths(Point p, int num)
    {
        boolean b = false;
        int pathTiles = 0;
        int x = p.getX();
        int y = p.getY();

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS NOT A CORNER OR SIDE
        ////////////////////////////////////////////////
        if (x > 0 && x < width - 1 && y > 0 && y < height - 1)
        {
            if (p.getRight() == 2)
                pathTiles++;
            if (p.getLeft() == 2)
                pathTiles++;
            if (p.getUp() == 2)
                pathTiles++;
            if (p.getDown() == 2)
                pathTiles++;
        }

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS A SIDE && NOT A CORNER
        ////////////////////////////////////////////////
        if (y == 0 && x > 0 && x < width - 1)
        {
            if (p.getDown() == 2)
                pathTiles++;
            if (p.getRight() == 2)
                pathTiles++;
            if (p.getLeft() == 2)
                pathTiles++;
        }
        if (x == 0 && y > 0 && y < height - 1)
        {
            if (p.getRight() == 2)
                pathTiles++;
            if (p.getUp() == 2)
                pathTiles++;
            if (p.getDown() == 2)
                pathTiles++;
        }
        if (y == height - 1 && x > 0 && x < width - 1)
        {
            if (p.getUp() == 2)
                pathTiles++;
            if (p.getRight() == 2)
                pathTiles++;
            if (p.getLeft() == 2)
                pathTiles++;
        }
        if (x == width - 1 && y > 0 && y < height - 1)
        {
            if (p.getLeft() == 2)
                pathTiles++;
            if (p.getDown() == 2)
                pathTiles++;
            if (p.getUp() == 2)
                pathTiles++;
        }

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS ONLY A CORNER
        ////////////////////////////////////////////////
        if (x == 0 && y == 0)
        {
            if (p.getRight() == 2)
                pathTiles++;
            if (p.getDown() == 2)
                pathTiles++;
        }
        if (x == 0 && y == height - 1)
        {
            if (p.getRight() == 2 || p.getUp() == 2)
                pathTiles++;
            if (p.getUp() == 2)
                pathTiles++;
        }
        if (x == width - 1 && y == 0)
        {
            if (p.getLeft() == 2 || p.getDown() == 2)
                pathTiles++;
            if (p.getDown() == 2)
                pathTiles++;
        }
        if (x == width - 1 && y == height - 1)
        {
            if (p.getLeft() == 2 || p.getUp() == 2)
                pathTiles++;
            if (p.getUp() == 2)
                pathTiles++;
        }

        if (pathTiles > num)
            b = true;
        return b;
    }

    private boolean hasWallCornerAndOppositeCornerPath(Point p)
    {
        boolean b = false;
        int x = p.getX();
        int y = p.getY();

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS NOT A CORNER OR SIDE
        ////////////////////////////////////////////////
        if (x > 0 && x < width - 1 && y > 0 && y < height - 1)
        {
            if (p.getRight() == 3 && p.getDown() == 3 && p.getLeft() != 3 && p.getUpLeft() == 2)
                b = true;
            else if (p.getRight() != 3 && p.getDown() == 3 && p.getLeft() == 3 && p.getUpRight() == 2)
                b = true;
            else if (p.getRight() == 3 && p.getDown() != 3 && p.getLeft() != 3 && p.getDownLeft() == 2)
                b = true;
            else if (p.getRight() != 3 && p.getDown() != 3 && p.getLeft() == 3 && p.getDownRight() == 2)
                b = true;
        }

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS A SIDE && NOT A CORNER
        ////////////////////////////////////////////////
        else if (y == 0 && x > 0 && x < width - 1)
        {
            if (p.getLeft() == 3 && p.getRight() != 3 && p.getDownRight() == 2)
                b = true;
            else if (p.getLeft() != 3 && p.getRight() == 3 && p.getDownLeft() == 2)
                b = true;
        }
        else if (x == 0 && y > 0 && y < height - 1)
        {
            if (p.getUp() == 3 && p.getDown() != 3 && p.getDownRight() == 2)
                b = true;
            else if (p.getDown() == 3 && p.getUp() != 3 && p.getUpRight() == 2)
                b = true;

        }
        else if (y == height - 1 && x > 0 && x < width - 1)
        {
            if (p.getLeft() == 3 && p.getRight() != 3 && p.getUpRight() == 2)
                b = true;
            else if (p.getLeft() != 3 && p.getRight() == 3 && p.getUpLeft() == 2)
                b = true;
        }
        else if (x == width - 1 && y > 0 && y < height - 1)
        {
            if (p.getUp() == 3 && p.getDown() != 3 && p.getDownLeft() == 2)
                b = true;
            else if (p.getDown() == 3 && p.getUp() != 3 && p.getUpLeft() == 2)
                b = true;
        }

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS ONLY A CORNER
        ////////////////////////////////////////////////
        else if (x == 0 && y == 0 && p.getRight() != 3 && p.getDown() != 3 && grid[x + 1][y + 1] == 2)
        {
            b = true;
        }
        else if (x == 0 && y == height - 1 && p.getUp() != 3 && p.getRight() != 3 && grid[x + 1][y - 1] == 2)
        {
            b = true;
        }
        else if (x == width - 1 && y == 0 && p.getDown() != 3 && p.getLeft() != 3 && grid[x - 1][y + 1] == 2)
        {
            b = true;
        }
        else if (x == width - 1 && y == height - 1 && p.getUp() != 3 && p.getLeft() != 3 && grid[x - 1][y - 1] == 2)
        {
            b = true;
        }

        return b;
    }

    private boolean hasAdjacentStart(Point p)
    {
        boolean b = false;
        int x = p.getX();
        int y = p.getY();

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS NOT A CORNER OR SIDE
        ////////////////////////////////////////////////
        if (x > 0 && x < width - 1 && y > 0 && y < height - 1)
        {
            if (p.getRight() == -1 || p.getLeft() == -1 || p.getUp() == -1 || p.getDown() == -1)
                b = true;
        }

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS A SIDE && NOT A CORNER
        ////////////////////////////////////////////////
        else if (y == 0 && x > 0 && x < width - 1)
        {
            if (p.getDown() == -1 || p.getRight() == -1 || p.getLeft() == -1)
                b = true;
        }
        else if (x == 0 && y > 0 && y < height - 1)
        {
            if (p.getRight() == -1 || p.getUp() == -1 || p.getDown() == -1)
                b = true;
        }
        else if (y == height - 1 && x > 0 && x < width - 1)
        {
            if (p.getUp() == -1 || p.getRight() == -1 || p.getLeft() == -1)
                b = true;
        }
        else if (x == width - 1 && y > 0 && y < height - 1)
        {
            if (p.getLeft() == -1 || p.getDown() == -1 || p.getUp() == -1)
                b = true;
        }

        ////////////////////////////////////////////////
        //ANY SPACE THAT IS ONLY A CORNER
        ////////////////////////////////////////////////
        else if (x == 0 && y == 0)
        {
            if (p.getRight() == -1 || p.getDown() == -1)
                b = true;
        }
        else if (x == 0 && y == height - 1)
        {
            if (p.getRight() == -1 || p.getUp() == -1)
                b = true;
        }
        else if (x == width - 1 && y == 0)
        {
            if (p.getLeft() == -1 || p.getDown() == -1)
                b = true;
        }
        else if (x == width - 1 && y == height - 1)
        {
            if (p.getLeft() == -1 || p.getUp() == -1)
                b = true;
        }
        return b;
    }
}
