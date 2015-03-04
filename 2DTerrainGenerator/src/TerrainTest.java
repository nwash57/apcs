public class TerrainTest
{
    public static void main(String[] args)
    {
        Terrain t = new Terrain(16, 8);
        t.fillPointArray();
        t.initiate();
        t.getNextTile();
        t.getNextTile();
        t.getNextTile();
        t.getNextTile();
        t.getNextTile();
        t.printTerrain();
    }
}
