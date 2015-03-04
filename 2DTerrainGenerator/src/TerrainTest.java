public class TerrainTest
{
    public static void main(String[] args)
    {
        Terrain t = new Terrain(64, 16);
        t.fillPointArray();
        t.printTerrain();
    }
}
