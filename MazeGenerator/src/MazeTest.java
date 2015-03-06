//this is a test

public class MazeTest
{
    public static void main(String[] args)
    {
	    /*
	    0: debug
	    1: toTxt
	    2: toPng
	    3: finalToTxt
	    4: finalToPng
	     */
	    boolean[] ms = new boolean[5];
	    ms[0] = false;
	    ms[1] = false;
	    ms[2] = false;
	    ms[3] = true;
	    ms[4] = true;

	    System.out.println("loading|....................................................................................................|");
	    System.out.print("       |");
        Maze m = new Maze(300, 300, 5, ms[0], ms[1], ms[2]);
        m.generateGrid();
        m.fillGrid();
	    m.fillExtraPath();
	    m.fillOpen();
	    m.printMaze();

	    if (ms[3])
		    m.printMazeToTxt();
	    if (ms[4])
		    m.printMazeToImage();
    }
}
