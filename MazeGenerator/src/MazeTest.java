public class MazeTest
{
    public static void main(String[] args)
    {
        Maze m = new Maze(16, 16);
        m.generateGrid();
        m.fillGrid();
        m.printMaze();
	    m.fillExtraPath();
	    m.fillOpen();
	    m.printMaze();
    }
}
