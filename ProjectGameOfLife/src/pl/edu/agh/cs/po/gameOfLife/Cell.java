package pl.edu.agh.cs.po.gameOfLife;

public class Cell {
    private Vector2d position;
    private Map map;
    public Cell(Vector2d position, Map map) {
        this.position = position;
        this.map = map;
    }
    public Vector2d getPosition()
    {
        return this.position;
    }
    public Cell revive()
    {
        return new Cell(this.position, this.map);
    }
    public String toString()
    {
        return "#";
    }
}
