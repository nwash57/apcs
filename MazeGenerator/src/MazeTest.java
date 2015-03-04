//this is a test

public class MazeTest
{
    public static void main(String[] args)
    {
        Maze m = new Maze(16, 16);
        m.generateGrid();
        m.fillGrid();
	    m.fillExtraPath();
	    m.fillOpen();
	    m.printMaze();
    }
}
