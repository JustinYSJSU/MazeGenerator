package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

//Cell class

public class Cell 
{
	private boolean[] walls;
	private int xCoord;
	private int yCoord;
	private String color;
	private Cell parent;
	private HashSet<Cell> neighbors;
	private LinkedList<Cell> adjacencyList;
	private ArrayList<Cell> adjacencyHolder;
	private boolean pathTo = false;
	
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	private static final int NOT_NEIGHBOR = 4;
	
	/*
	 * Constructor for the Cell. Sets it's x and y coordinates based off the grid. 
	 * Initializes the boolean array for wall directions, and the color to be white
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * 
	 */
	public Cell(int x, int y)
	{
		this.xCoord = x;
		this.yCoord = y;
		walls = new boolean[4]; //4 directions: north, east, south, west 
		color = "White"; //set to white, hasn't been checked yet
		neighbors = new HashSet<Cell>();
		adjacencyList = new LinkedList<Cell>();
		adjacencyHolder = new ArrayList<Cell>();
		parent = null;
	}
	
	/*
	 * Gets all neighbors of a given cell that have all of their walls intact
	 * A cell is only considered a neighbor if it has all of it's walls intact and there
	 * is not already a path that leads to it 
	 * 
	 * @param c The current grid of cells
	 * 
	 */
	public HashSet<Cell> getNeighbors(Cell[][] c)
	{
		 if(this.getX() - 1 >= 0 && c[this.getX() - 1][this.getY()].wallCheck() == true && c[this.getX() - 1][this.getY()].getPathTo() == false) //has an northern neighbor
		 {
			 this.neighbors.add(c[this.getX() - 1][this.getY()]); //add to neighbors
			 c[this.getX() - 1][this.getY()].pathTo = true;
		 } 
		 
		 if(this.getY() + 1 < c.length && c[this.getX()][this.getY() + 1].wallCheck() == true && c[this.getX()][this.getY() + 1].getPathTo() == false) //has an eastern neighbor
		 {
			 this.neighbors.add(c[this.getX()][this.getY() + 1]);  //add to neighbors
			 c[this.getX()][this.getY() + 1].pathTo = true;
		 }
		 
		 if(this.getX() + 1 < c[0].length && c[this.getX() + 1][this.getY()].wallCheck() == true &&  c[this.getX() + 1][this.getY()].getPathTo() == false) //has an southern neighbor
		 {
			 this.neighbors.add(c[this.getX() + 1][this.getY()]); //add to neighbors
			 c[this.getX() + 1][this.getY()].pathTo = true;
		 }
		 
		 if(this.getY() - 1 >= 0 && c[this.getX()][this.getY() - 1].wallCheck() == true && c[this.getX()][this.getY() - 1].getPathTo() == false) //has western neighbor
		 {
			 this.neighbors.add(c[this.getX()][this.getY() - 1]); //add to neighbors
			 c[this.getX()][this.getY() - 1].pathTo = true;
		 }
		 return neighbors;
	}
	
	/*
	 * Gets all adjacent cells of a given cell that have all of their walls intact
	 * A cell is only considered adjacent if it is next to the current cell 
	 * and the wall between it and the currentCell has been broken
	 * 
	 * @param c The current grid of cells
	 * 
	 */
	public void getAdjacent(Cell[][] c)
	{
		 if(this.getX() - 1 >= 0 && c[this.getX() - 1][this.getY()].getWalls()[SOUTH] == true && this.getWalls()[NORTH] == true) //has an adjacent cell to the north
		 {
			 this.adjacencyHolder.add(c[this.getX() - 1][this.getY()]); 
		 } 
		 
		 if(this.getY() + 1 < c.length && c[this.getX()][this.getY() + 1].getWalls()[WEST] && this.getWalls()[EAST] == true) //has an adjacent cell to the east
		 {
			 this.adjacencyHolder.add(c[this.getX()][this.getY() + 1]); 
		 }
		 
		 if(this.getX() + 1 < c[0].length && c[this.getX() + 1][this.getY()].getWalls()[NORTH] == true &&  this.getWalls()[SOUTH] == true) //has an southern neighbor
		 {
			 this.adjacencyHolder.add(c[this.getX() + 1][this.getY()]); 
		 }
		 
		 if(this.getY() - 1 >= 0 && c[this.getX()][this.getY() - 1].getWalls()[EAST] == true && this.getWalls()[WEST] == true) //has western neighbor
		 {
			 this.adjacencyHolder.add(c[this.getX()][this.getY() - 1]); 
		 }
	}
	
	/*
	 * Identifies the directional relationship between a node and one of it's neighbors
	 * northern neighbor, easter neighbor, etc
	 *
	 *@param the given neighbor cell
	 *
	 *@return constant representing the direction
	 *
	 */
	public int identifyDirectionRelationship(Cell neighbor)
	{
		int direction = NOT_NEIGHBOR; //0 - 3 are taken by the directional values
		
		if(this.getX() - 1 == neighbor.getX()) //neighbor is to the north
		{
			direction = NORTH;
		}
	
		if(this.getY() + 1 == neighbor.getY()) //neighbor is to the east
		{
			direction = EAST;
		}
		
		if(this.getX() + 1 == neighbor.getX()) //neighbor is to the south
		{
			direction = SOUTH;
		}
		
		if(this.getY() - 1 == neighbor.getY()) //neighbor is to the west
		{
			direction = WEST;
		}
		return direction;
	}
	
	/*
	 * Removes the wall of the certain direction of a cell
	 * 
	 * @param x The direction of the wall (0 = north, 1 = east, 2 = south, 3 = west)
	 */
	public void removeWall(int x, Cell neighbor) 
	{
		if(x == NORTH) //northern neighbor, remove northern wall of current, southern wall of neighbor
		{
			this.walls[NORTH] = true;
			neighbor.walls[SOUTH] = true;
		}
		
		if(x == EAST) //eastern neighbor, remove eastern wall of current, western wall of neighbor
		{
			this.walls[EAST] = true;
			neighbor.walls[WEST] = true;
		}
		
		if(x == SOUTH) //souther neighbor, remove southern wall of current, northern wall of neighbor
		{
			this.walls[SOUTH] = true;
			neighbor.walls[NORTH] = true;
		}
		
		if(x == WEST) //western neighbor, remove western wall of current, eastern wall of neighbor
		{
			this.walls[WEST] = true;
			neighbor.walls[EAST] = true;
		}
	}
	
	/*
	 * Checks the wall validity of a cell
	 * 
	 * @return true if all the walls are up
	 * @return false if all the walls are not up
	 * 
	 */
	public boolean wallCheck()
	{
		for(int i = 0; i < this.walls.length; i++)
		{
			if(walls[i] == true)
			{
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Sets the parent of the Cell
	 * 
	 * @param p The parent of the cell
	 * 
	 */
	public void setParent(Cell p)
	{
		this.parent = p;
	}
	
	/*
	 * Changes the color of the cell
	 * 
	 * @param c The new color of the cell
	 * 
	 */
	public void changeColor(String c)
	{
		this.color = c;
	}
	
	/*
	 * Gets the x coordinate
	 * 
	 * @return x coordinate of the cell
	 * 
	 */
	public int getX()
	{
		return this.xCoord;
	}
	
	/*
	 * Gets the y coordinate
	 * 
	 * @return x coordinate of the cell
	 * 
	 */
	public int getY()
	{
		return this.yCoord;
	}
	
	/*
	 * Sets the color
	 * 
	 * @param c The new color
	 * 
	 */
	public void setColor(String c)
	{
		this.color = c;
	}
	
	/*
	 * Gets the color
	 * 
	 * @return color of the cell
	 * 
	 */
	public String getColor()
	{
		return this.color;
	}
	
	/*
	 * Gets the parent
	 * 
	 * @return parent of the cell
	 * 
	 */
	public Cell getParent()
	{
		return this.parent;
	}
	
	/*
	 * Gets the wall values for a cell
	 * 
	 */
	public boolean[] getWalls()
	{
		return this.walls;
	}
	
	/*
	 * Gets adjacency list
	 * 
	 */
	public LinkedList<Cell> getAdjacencyList() 
	{
		return adjacencyList;
	}

	public boolean getPathTo() 
	{	
		return pathTo;
	}
	
	/*
	 * Gets the array list of adjacent cells
	 * 
	 */
	public ArrayList<Cell> getAdjacencyHolder()
	{
		return this.adjacencyHolder;
	}
	
}
