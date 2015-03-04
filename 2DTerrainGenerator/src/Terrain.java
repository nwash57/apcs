import java.util.ArrayList;
import java.util.Random;

/* GRID KEY
 * -1 = ground
 * 0 = open
 * 1 = sky
*/

public class Terrain
{
    private Random rand = new Random();

    private int width, height, maxHeight, minHeight, currentHeight;
    private Point[] grid;
    private Point lastPoint;

    private ArrayList<Point> openPoints = new ArrayList<Point>();
    private ArrayList<Point> groundPoints= new ArrayList<Point>();
    private ArrayList<Point> skyPoints = new ArrayList<Point>();

    public Terrain(int w, int h)
    {
        width = w;
        height = h;
        grid = new Point[width * height];
    }

    public void fillPointArray()
    {
        int i = 0;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                Point p = new Point(x, y);
                grid[i] = p;
                i++;
            }
        }

        for (Point p : grid)
            openPoints.add(p);
    }

    public void initiate()
    {
        int initialHeight = rand.nextInt(height);

        for (Point p : grid)
        {
            if (p.getX() == 0 && p.getY() == initialHeight)
                setGroundPoint(p);
        }
    }

    public void getNextTile()
    {
        for (Point p : grid)
            if (p.getX() == (lastPoint.getX() + 1) && p.getY() == (lastPoint.getY() + upOrDown()))
            {
                setGroundPoint(p);
                break;
            }
    }

    private void setGroundPoint(Point p)
    {
        p.setType(-1);
        openPoints.remove(p);
        groundPoints.add(p);
        lastPoint = p;
    }

    private int upOrDown()
    {
        int i;
        boolean b = rand.nextBoolean();

        if (b)
            i = 1;
        else i = -1;

        return i;
    }

    public void printTerrain()
    {
        int currentRow = 0;

        System.out.println();

        for (Point p : grid)
        {
            if (p.getY() == currentRow)
            {
                System.out.print(p.toString());
            }
            else
            {
                System.out.println();
                System.out.print(p.toString());
                currentRow++;
            }
        }
    }
}
