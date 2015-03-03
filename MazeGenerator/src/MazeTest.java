//this is a git test

public class MazeTest
{
    public static void main(String[] args)
    {
        Maze m = new Maze(15, 15);
        m.generateGrid();
        m.fillGrid();
        m.printMaze();
    }
}
