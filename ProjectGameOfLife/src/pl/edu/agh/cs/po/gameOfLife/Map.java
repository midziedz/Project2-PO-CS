package pl.edu.agh.cs.po.gameOfLife;

import java.util.*;

public class Map {
    private LinkedHashMap<Vector2d, Cell> cells = new LinkedHashMap<>();
    private List<Integer> toSurvive = new ArrayList<>();
    private List<Integer> toRevive = new ArrayList<>();
    private Vector2d lowLeft = new Vector2d(0, 0);
    private Vector2d upRight = new Vector2d(0, 0);
    private MapVisualizer mapVisualizer = new MapVisualizer(this);
    private int simulationSpeed;
    public Map(List<Integer> toSurvive, List<Integer> toRevive, List<Vector2d> initialCells, int simulationSpeed)
    {
        this.toSurvive = toSurvive;
        this.toRevive = toRevive;
        this.simulationSpeed = simulationSpeed;
        for(Vector2d cell: initialCells)
        {
            this.cells.put(cell, new Cell(cell, this));
            this.lowLeft = this.lowLeft.lowerLeft(cell);
            this.upRight = this.upRight.upperRight(cell);
        }
    }
    private int countAliveNeighbours(Vector2d position)
    {
        int counter = 0;
        MapDirection toSearch = MapDirection.NORTH;
        for(int j = 0; j < 8; j++)
        {
            if(this.cells.get(position.add(toSearch.toUnitVector())) != null)
            {
                counter += 1;
            }
            toSearch = toSearch.next();
        }
        return counter;
    }
    public void killAndReviveAllPossible()
    {
        List<Vector2d> victimList = new ArrayList<>();
        List<Vector2d> revivingList = new ArrayList<>();
        for(Cell cell: this.cells.values())
        {
            Vector2d position = cell.getPosition();
            //System.out.println(position);
            int howManyAliveNeighbours = countAliveNeighbours(position);
            //System.out.println(howManyAliveNeighbours);
            boolean toKill = true;
            for(Integer survivingNumber: this.toSurvive)
            {
                if(howManyAliveNeighbours == survivingNumber)
                {
                    toKill = false;
                    break;
                }
            }
            if(toKill)
            {
                victimList.add(position);
            }
            reviveNearby(position, revivingList);
        }
        for(Vector2d revived: revivingList)
        {
            this.cells.put(revived, new Cell(revived, this));
            this.lowLeft = this.lowLeft.lowerLeft(revived);
            this.upRight = this.upRight.upperRight(revived);
        }
        for(Vector2d killed: victimList)
        {
            this.cells.remove(killed);
        }
    }
    private void reviveNearby(Vector2d position, List<Vector2d> revivingList)
    {
        MapDirection toCheck = MapDirection.NORTH;
        for(int i = 0; i < 9; i++)
        {
            Vector2d checkPosition = position.add(toCheck.toUnitVector());
            int howManyAliveNeighbours = countAliveNeighbours(checkPosition);
            boolean toRevive = false;
            for(Integer revivingNumber: this.toRevive)
            {
                if(howManyAliveNeighbours == revivingNumber)
                {
                    toRevive = true;
                    break;
                }
            }
            if(toRevive && this.cells.get(checkPosition) == null)
            {
                revivingList.add(checkPosition);
            }
            toCheck = toCheck.next();
        }
    }
    public Object objectAt(Vector2d position)
    {
        return this.cells.get(position);
    }
    @Override
    public String toString()
    {
        return this.mapVisualizer.draw(this.lowLeft, this.upRight);
    }
    public void oneDayGone() throws InterruptedException
    {
        System.out.println(this.toString());
        Thread.sleep(this.simulationSpeed);
        this.killAndReviveAllPossible();
    }
}