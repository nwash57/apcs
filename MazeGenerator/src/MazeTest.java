//this is a test

import java.util.Scanner;

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
	    ms[3] = false;
	    ms[4] = false;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter grid width: ");
        int width = scan.nextInt();
        System.out.println("Enter grid height: ");
        int height = scan.nextInt();
        System.out.println("Enter maximum path length: ");
        int pathLength = scan.nextInt();
        System.out.println();

        Maze m = new Maze(width, height, pathLength, ms[0], ms[1], ms[2]);
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
